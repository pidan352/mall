<template>
    <a-layout style="padding: 24px 24px 24px">
        <a-layout style="background: #fff;padding: 24px; margin: 0;min-height: 280px">
            <div>
                <a-button type="primary" @click="selectBrand" style="margin-right: 6px">刷新</a-button>
                <a-button type="primary" style="margin-right: 6px">添加</a-button>
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
</template>

<script lang="ts">
    import axios from "axios";
    import {defineComponent, ref, onMounted, computed, reactive, toRefs} from 'vue';
    import {message} from 'ant-design-vue';
    import {QuestionCircleOutlined} from '@ant-design/icons-vue';

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
            dataIndex: 'id',
            width: '20%'
        },
        {
            title: '品牌',
            dataIndex: 'name',
            width: '50%',
        },
        {
            title: '品牌首字母',
            dataIndex: 'firstChar',
            width: '30%'
        }
    ]


    //数据加载标志
    const loading = ref(false);

    //分页大小自定义数据
    const pageSizeOptions = ['5', '10', '15', '20']


    //复选框参数
    type Key = string | number;

    interface DataType {
        key: Key;
        id: number;
        name: string;
        firstChar: string;
    }

    interface brand {
        id: number;
        name: string;
        firstChar: string;
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

    export default defineComponent({
        name: 'Brand',

        components: {
            QuestionCircleOutlined,
        },


        setup: function () {
            //数据查询
            const selectBrand = (params: any) => {
                //数据正在加载中，显示数据的地方会有加载中的动画
                loading.value = true;
                axios.get('http://localhost:8082/brand-ms/queryBrandByPage', {
                    params: {
                        page: params.page,
                        size: params.size,
                    }
                }).then((response) => {
                    loading.value = false
                    if (response.data.code === 200) {
                        //使用了复选框，重装表格的数据。删除了brandList
                        const brandArray = response.data.data.list;
                        if (brandArray.length == 0) {
                            //数据库无数据
                            errormessage.value = "无数据"
                            //将之前存储的数据清空，否则删除最后剩下的数据时数据库数据已删除，但页面数据还在
                            data.value = null
                        } else {
                            const dataSource: DataType[] = []
                            for (let i = 0; i < brandArray.length; i++) {
                                dataSource.push({
                                    //商品在数据库的id作为复选框的行标志
                                    key: brandArray[i].id,
                                    //因为每次只查五条数据，所以需要计算编号
                                    id: (pagination.value.current - 1) * pagination.value.pageSize + i + 1,
                                    name: brandArray[i].name,
                                    firstChar: brandArray[i].firstChar,
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
                axios.post('http://localhost:8082/brand-ms/deleteBrand', idList).then((response) => {
                    if (response.data.code === 200) {
                        //删除完毕
                        setTimeout(() => {
                            state.loading2 = false;
                            state.selectedRowKeys = [];
                            selectBrand({
                                page: 1,
                                size: pagination.value.pageSize
                            })
                        }, 1000);
                        message.success(response.data.message)
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

</style>