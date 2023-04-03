<template>
    <a-layout style="padding: 24px 24px 24px">
        <a-layout style="background: #fff;padding: 24px; margin: 0;min-height: 280px">
            <div>
                <a-button type="primary" @click="selectBrand" style="margin-right: 6px">刷新</a-button>
                <a-button type="primary" style="margin-right: 6px">添加</a-button>
                <a-button type="primary" :disabled="!hasSelected" :loading="loading2" @click="start">删除</a-button>
                <span style="margin-left: 8px">
                    <template v-if="hasSelected">
                      {{ `共 ${selectedRowKeys.length} 条` }}
                    </template>
                </span>
            </div>
            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="data"
                    :pagination="false"
                    :loading="loading"
                    class="ant-table-striped"
                    :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
                    :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: deleteBefore }"
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
            //列数据在数据项中对应的路径，支持通过数组查询嵌套路径
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

    const data = ref()

    export default defineComponent({
        name: 'Brand',


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

                    //使用了复选框，重装表格的数据。删除了brandList
                    const brandArray = response.data.list;
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
                    pagination.value.total = response.data.total
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
            //先提示
            const start = () => {
                state.loading2 = true;

                //selectedRowKeys是一个Proxy代理对象，使用序列化的方法将其转化为数组类型
                const idList: [number] = JSON.parse(JSON.stringify(state.selectedRowKeys))
                axios.post('http://localhost:8082/brand-ms/deleteBrand', idList).then((response) => {
                    console.log(idList)
                })

                //删除完毕
                setTimeout(() => {
                    state.loading2 = false;
                    state.selectedRowKeys = [];
                }, 1000);
            };
            //复选框变动触发的函数
            const onSelectChange = (selectedRowKeys: Key[]) => {
                // console.log('selectedRowKeys changed: ', selectedRowKeys);
                state.selectedRowKeys = selectedRowKeys;
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