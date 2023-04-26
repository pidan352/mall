<template>
    <a-layout style="padding: 24px 24px 24px">
        <a-layout style="background: #fff;padding: 24px; margin: 0;min-height: 280px">
            <div>
                <a-button type="primary"
                          @click="selectBrand({
                            page: 1,
                            size: pagination.pageSize
                          })"
                          style="margin-right: 6px">刷新
                </a-button>

                <a-button type="primary" style="margin-right: 6px" @click="showModal">添加</a-button>

                <a-popconfirm
                        v-bind:title="title"
                        cancelText="取消"
                        ok-text="确定"
                        @cancel="cancel"
                        @confirm="start"
                >
                    <template #icon>
                        <question-circle-outlined style="color: red"/>
                    </template>
                    <a-button type="primary" :disabled="!hasSelected" :loading="loading2">删除</a-button>
                    <span style="margin-left: 8px">
                    <template v-if="hasSelected">
                      {{ `共 ${selectedRowKeys.length} 条` }}
                    </template>
                </span>
                </a-popconfirm>
            </div>

            <a-table
                    :columns="columns"
                    :data-source="data"
                    :pagination="false"
                    :loading="loading"
                    class="ant-table-striped"
                    :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
                    :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
                    :locale="{emptyText:errormessage}"
            >
                <template #bodyCell="{ column, text, record }">
                    <template v-if="['categoryId','title','url','status','sortOrder'].includes(column.dataIndex)">
                        <div>
                            <a-input
                                    v-if="editableData[record.key]"
                                    v-model:value="editableData[record.key][column.dataIndex]"
                                    style="margin: -5px 0"
                            />
                            <template v-else>
                                {{ text }}
                            </template>
                        </div>
                    </template>

                    <template v-else-if="column.dataIndex === 'pic'">
                        <div class="editable-row-operations" style="width: 300px;height: 120px">
                            <!--                            <div>-->
                            <div :style="'background:url('+record.pic+') no-repeat center;background-size:contain;width: 100%;height: 100%;'">
                                <!--<img :src="record.pic" style="object-fit:cover;width: 100%;height: 100%;">-->
                            </div>
                        </div>
                    </template>

                    <template v-else-if="column.dataIndex === 'operation'">
                        <div class="editable-row-operations">
                            <span v-if="editableData[record.key]">
                                 <a-typography-link @click="save(record.key)">保存</a-typography-link>
                                <a-popconfirm title="确定取消吗?"
                                              @confirm="cancelEdit(record.key)"
                                              ok-text="确定"
                                              cancel-text="取消"
                                >
                                  <a>取消</a>
                                </a-popconfirm>
                            </span>
                            <span v-else>
                                <a @click="edit(record.key)">编辑</a>
                            </span>
                        </div>
                    </template>
                </template>
            </a-table>
        </a-layout>

        <div style="position: relative;height: 28px">
            <a-pagination style="position: absolute;top: 16px;right: 0px"
                          v-model:current="pagination.current"
                          v-model:pageSize="pagination.pageSize"
                          :total="pagination.total"
                          :show-total="total=>`总共 ${total} 条数据`"
                          show-size-changer
                          :page-size-options="pageSizeOptions"
                          @change="(current,pageSize)=>handleTableChange(current,pageSize)"
            />
        </div>
    </a-layout>


    <!-- 弹出的添加对话框 -->
    <a-modal
            v-model:visible="visible"
            title="添加品牌"
            :confirm-loading="confirmLoading"
            @ok="handleOk"
            @cancel="modelCancel"
            cancel-text="取消"
            ok-text="添加"
    >
        <a-form layout="vertical" name="form_in_modal" v-model:model="brandObject" ref="formRef">
            <a-form-item
                    name="categoryId"
                    label="广告类别"
                    :rules="[{ required: true, message: '请选择广告类别' }]"
            >
                <!--
                    getPopupContainer为了防止滚动时select与输入框分离的bug
                    labelInValue使选中时返回值的类型变为{key:key,value:value,...}
                 -->
                <a-select v-model:value="brandObject.categoryId"
                          placeholder="请选择广告类别"
                          :getPopupContainer="triggerNode => {
                            return triggerNode.parentNode || document.body
                            }"
                          labelInValue
                >
                    <a-select-option v-for="(category) in categoryList"
                                     :value="category.name"
                                     v-bind:key="category.id">
                    </a-select-option>
                </a-select>
            </a-form-item>

            <a-form-item name="title" label="标题">
                <a-input v-model:value="brandObject.title"/>
            </a-form-item>

            <a-form-item name="url" label="链接">
                <a-input v-model:value="brandObject.url"/>
            </a-form-item>

            <a-form-item name="pic" label="图片">
                <a-upload
                        v-model:file-list="brandObject.pic"
                        action="http://localhost:8082/content-ms/uploadPic"
                        list-type="picture-card"
                        @preview="handlePreview"
                >
                    <div v-if="brandObject.pic.length < 1">
                        <plus-outlined/>
                        <div style="margin-top: 8px">Upload</div>
                    </div>
                </a-upload>
                <a-modal :visible="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
                    <img alt="example" style="width: 100%" :src="previewImage"/>
                </a-modal>
            </a-form-item>

            <a-form-item name="status" label="状态">
                <a-input v-model:value="brandObject.status"/>
            </a-form-item>

            <a-form-item name="sortOrder" label="排序">
                <a-input v-model:value="brandObject.sortOrder" placeholder="input number"/>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script lang="ts">
    import axios from "axios";
    import {defineComponent, ref, onMounted, computed, reactive, toRefs, toRaw, watch} from 'vue';
    import {message} from 'ant-design-vue';
    import {QuestionCircleOutlined, PlusOutlined} from '@ant-design/icons-vue';
    import type {FormInstance, UploadProps} from 'ant-design-vue';
    import type {UnwrapRef} from 'vue';
    import {cloneDeep} from 'lodash-es';

    //定义数据，双向绑定需要用ref
    //table参数
    const pagination = ref({
        current: 1,
        pageSize: 5,
        total: 0
    });
    const columns = [
        {
            title: '编号',
            //列数据在数据项中对应的路径，支持通过数组查询嵌套路径。有了dataIndex可以将key忽略
            dataIndex: 'key',
            width: '5%'
        },
        {
            title: '类别id',
            dataIndex: 'categoryId',
            width: '5%',
        },
        {
            title: '标题',
            dataIndex: 'title',
            width: '15%'
        },
        {
            title: '链接',
            dataIndex: 'url',
            width: '25%'
        },
        {
            title: '图片',
            dataIndex: 'pic',
            width: '20%'
        },
        {
            title: '状态',
            dataIndex: 'status',
            width: '10%'
        },
        {
            title: '排序',
            dataIndex: 'sortOrder',
            width: '10%'
        },
        {
            title: '操作',
            dataIndex: 'operation',
            width: '10%'
        },
    ]


    //数据加载标志
    const loading = ref(false);

    //分页大小自定义数据
    const pageSizeOptions = ['5', '10', '15', '20']


    //复选框参数
    type Key = string | number;

    interface DataType {
        id: number;
        key: Key;
        categoryId: number;
        title: string;
        url: string;
        pic: string;
        status: number;
        sortOrder: number;
    }

    interface content {
        //表示可以不用赋初值
        id?: number;
        categoryId?: number;
        title: string;
        url: string;
        pic: string;
        status: number;
        sortOrder: number;
    }

    interface category {
        id?: number;
        name: string;
    }

    //表格的数据源
    /*
    在 Table 中，dataSource 和 columns 里的数据值都需要指定 key 值。对于 dataSource 默认将每列数据的 key
    属性作为唯一的标识。如果你的数据没有这个属性，务必使用 rowKey 来指定数据列的主键。若没有指定，控制台会出现缺
    少 key 的提示，表格组件也会出现各类奇怪的错误。
     */
    const data = ref()

    //删除确认框的提示信息
    const title = ref()

    //错误信息
    const errormessage = ref()

    //添加品牌窗口的参数
    const visible = ref<boolean>(false);
    const confirmLoading = ref<boolean>(false);
    const formRef = ref<FormInstance>();
    //表单品牌对象
    const brandObject = reactive<Record<string, any>>({
        pic: []
    })

    const categoryList = ref()


    //编辑表格单元行参数
    //编辑时复制一份数据
    const editableData: UnwrapRef<Record<string, any>> = reactive({});


    //上传文件
    //编码函数
    function getBase64(file: File) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });
    }

    const previewVisible = ref(false);
    const previewImage = ref('');
    const previewTitle = ref('');

    // const fileList = ref<UploadProps['fileList']>([
    //     // {
    //     //     uid: '-1',
    //     //     name: 'image.png',
    //     //     status: 'done',
    //     //     url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
    //     // },
    // ]);


    export default defineComponent({
        name: 'Content',

        components: {
            QuestionCircleOutlined,
            PlusOutlined,
        },


        setup: function () {
            //数据查询
            const selectBrand = (params: any) => {
                //数据正在加载中，显示数据的地方会有加载中的动画
                loading.value = true;
                axios.get('http://localhost:8082/content-ms/queryContentByPage', {
                    params: {
                        page: params.page,
                        size: params.size,
                    }
                }).then((response) => {
                    loading.value = false
                    if (response.data.code === 200) {
                        //使用了复选框，重装表格的数据。删除了brandList
                        const contentArray = response.data.data.list;
                        if (contentArray.length == 0) {
                            //数据库无数据
                            errormessage.value = "无数据"
                            //将之前存储的数据清空，否则删除最后剩下的数据时数据库数据已删除，但页面数据还在
                            data.value = null
                        } else {
                            const dataSource: DataType[] = []
                            for (let i = 0; i < contentArray.length; i++) {
                                dataSource.push({
                                    //商品在数据库的id作为复选框的行标志
                                    id: contentArray[i].id,
                                    //因为每次只查五条数据，所以需要计算编号
                                    key: (params.page - 1) * params.size + i + 1,
                                    categoryId: contentArray[i].categoryId,
                                    title: contentArray[i].title,
                                    url: contentArray[i].url,
                                    pic: contentArray[i].pic,
                                    status: contentArray[i].status,
                                    sortOrder: contentArray[i].sortOrder
                                });
                            }
                            data.value = dataSource

                            //重置分页按钮
                            pagination.value.current = params.page
                            pagination.value.total = response.data.data.total
                        }
                    } else {
                        errormessage.value = response.data.message
                    }

                })
            }


            //页面初始化
            onMounted(() => {
                //体现一下加载动画
                loading.value = true;
                setTimeout(() => {
                    selectBrand({
                        page: 1,
                        size: pagination.value.pageSize
                    })
                }, 1000)
            })


            //分页、排序、筛选变化时触发
            const handleTableChange = (current: number, pageSize: number) => {
                selectBrand({
                    page: current,
                    size: pageSize
                })
            }

            //复选框
            //reactive定义:接收一个普通对象然后返回该普通对象的响应式代理。等同于 2.x 的 Vue.observable()
            const state = reactive<{ selectedRowKeys: Key[]; loading2: boolean; }>({
                selectedRowKeys: [],
                loading2: false,
            });

            //判断是否有选中复选框，无则按钮失效
            const hasSelected = computed(() => state.selectedRowKeys.length > 0);

            //删除选中行
            const start = () => {
                state.loading2 = true;

                //selectedRowKeys是一个Proxy代理对象，使用序列化的方法将其转化为数组类型
                const idList: [number] = JSON.parse(JSON.stringify(state.selectedRowKeys))
                axios.post('http://localhost:8082/content-ms/deleteContent', idList).then((response) => {
                    if (response.data.code === 200) {
                        //删除完毕
                        setTimeout(() => {
                            state.loading2 = false;
                            state.selectedRowKeys = [];
                            message.success(response.data.message)
                            selectBrand({
                                page: 1,
                                size: pagination.value.pageSize
                            })
                        }, 1000);
                    } else {
                        message.error(response.data.message)
                    }
                })
            };
            //复选框变动触发的函数
            const onSelectChange = (selectedRowKeys: Key[]) => {
                // console.log('selectedRowKeys changed: ', selectedRowKeys);
                state.selectedRowKeys = selectedRowKeys;
                title.value = "你确定删除这" + selectedRowKeys.length + "条数据吗？"
            };


            //删除动作取消事件
            const cancel = () => {
                state.selectedRowKeys = []
            }


            //弹出添加的窗口
            const showModal = () => {
                //查询广告类别
                axios.get("http://localhost:8082/content-ms/queryCategory")
                    .then((res) => {
                        if (res.data.code === 200) {
                            const categoryArray = res.data.data
                            const categoryData: category[] = []
                            if (categoryArray.length > 0) {
                                for (let i = 0; i < categoryArray.length; i++) {
                                    categoryData.push({
                                        id: categoryArray[i].id,
                                        name: categoryArray[i].name,
                                    })
                                }
                                categoryList.value = categoryData
                            } else {
                                categoryList.value = []
                            }
                        }
                    })
                visible.value = true;
            };

            //图片预览窗口关闭
            const handleCancel = () => {
                previewVisible.value = false;
                previewTitle.value = '';
            };
            //点击预览图片
            const handlePreview = async (file: any) => {
                if (!file.url && !file.preview) {
                    file.preview = (await getBase64(file.originFileObj)) as string;
                }
                previewImage.value = file.url || file.preview;
                previewVisible.value = true;
                previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
            };

            // 添加窗口的确认事件
            const handleOk = () => {
                //表单校验
                formRef.value?.validateFields()
                    .then(values => {
                        //因为select选中返回的值的格式是对象，但是只需要key值，所以单独提取出来
                        const form = cloneDeep(toRaw(brandObject))
                        form.categoryId = toRaw(brandObject).categoryId.key

                        //将图片上传返回的url放入表单信息中
                        if (toRaw(brandObject).pic.length > 0) {
                            form.pic = (toRaw(brandObject).pic)[0].response
                        } else {
                            form.pic = ''
                        }


                        confirmLoading.value = true;
                        axios.post('http://localhost:8082/content-ms/addContent', form).then((res) => {
                            setTimeout(() => {
                                if (res.data.code === 200) {
                                    message.success(res.data.message)
                                    selectBrand({
                                        page: 1,
                                        size: pagination.value.pageSize
                                    })
                                } else {
                                    message.error(res.data.message)
                                }
                            }, 1000)
                        }).catch(() => {
                            message.error('表单信息有误')
                        })
                        //重置表单
                        formRef.value?.resetFields();

                        confirmLoading.value = false
                        visible.value = false
                    })
            };

            //添加窗口的取消事件
            const modelCancel = () => {
                //重置表单
                formRef.value?.resetFields();
            }


            //编辑
            const edit = (key: string) => {
                //拷贝一份数据用作编辑
                editableData[key] = cloneDeep(data.value.filter((item: { key: string; }) => key === item.key)[0]);
            };

            //因为385行需要在请求完成之后执行所以需要将axios请求改为同步（默认是异步）,只需将请求使用await修饰并将
            //请求所在的函数用async修饰即可
            const save = async (key: string) => {
                await axios.post("http://localhost:8082/content-ms/editContent", editableData[key])
                    .then((res) => {
                        if (res.data.code === 200) {
                            //编辑成功，将编辑后的数据合并到源数据中
                            Object.assign(data.value.filter((item: { key: string; }) => key === item.key)[0], editableData[key]);
                        } else {
                            message.error(res.data.message)
                        }
                    })
                delete editableData[key];
            };

            const cancelEdit = (key: string) => {
                delete editableData[key];
            };

            return {
                pagination,
                columns,
                loading,
                selectBrand,
                handleTableChange,
                pageSizeOptions,
                data,
                hasSelected,
                ...toRefs(state),
                start,
                onSelectChange,
                title,
                cancel,
                errormessage,
                visible,
                confirmLoading,
                showModal,
                handleOk,
                modelCancel,
                brandObject,
                formRef,
                editingKey: '',
                editableData,
                edit,
                save,
                cancelEdit,
                categoryList,
                previewVisible,
                previewImage,
                // fileList,
                handleCancel,
                handlePreview,
                previewTitle,
            };
        },
    });
</script>

<style scoped>
    /*
    文档中使用的深度选择器是':deep'，然后这里提示没有这个选择器，但是网页内容正常，可能是本地的问题。
    */
    .ant-table-striped ::v-deep(.table-striped) td {
        background-color: #fafafa;
    }

    .editable-row-operations a {
        margin-right: 8px;
    }

    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }

</style>