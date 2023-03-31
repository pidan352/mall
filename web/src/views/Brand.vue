<template>
    <a-layout style="padding: 24px 24px 24px">
        <a-layout style="background: #fff;padding: 24px; margin: 0;min-height: 280px">
            <div>
                <a-button type="primary" style="margin-right: 6px">添加</a-button>
                <a-button type="primary" style="margin-right: 6px">删除</a-button>
                <a-button type="primary" @click="selectBrand">刷新</a-button>
            </div>
            <a-table
                    :columns="columns"
                    :row-key="record => record.id"
                    :data-source="brandList"
                    :pagination="false"
                    :loading="loading"
                    class="ant-table-striped"
                    :row-class-name="(_record, index) => (index % 2 === 1 ? 'table-striped' : null)"
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
    import {defineComponent, ref, onMounted} from 'vue';
    import {message} from 'ant-design-vue';


    //定义数据，双向绑定需要用ref
    //table参数
    const brandList = ref();
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

    const pageSizeOptions = ['5', '10', '15', '20']

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
                    brandList.value = response.data.list;

                    //重置分页按钮
                    pagination.value.current = params.page
                    pagination.value.total = response.data.total
                })
            }

            //页面初始化
            onMounted(() => {
                selectBrand({
                    page: 1,
                    size: pagination.value.pageSize
                })
            })

            //分页、排序、筛选变化时触发
            const handleTableChange = (current: number, pageSize: number) => {
                selectBrand({
                    page: current,
                    size: pageSize
                })
            }

            return {
                brandList,
                pagination,
                columns,
                loading,
                selectBrand,
                handleTableChange,
                pageSizeOptions,
            };
        },
    });
</script>

<style scoped>
    .ant-table-striped >>> .table-striped td {
        background-color: #fafafa;
    }
</style>