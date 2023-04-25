<template>
    <div class="clearfix">
        <a-upload
                v-model:file-list="fileList"
                action="http://localhost:8082/content-ms/uploadPic"
                list-type="picture-card"
                @preview="handlePreview"
        >
            <div v-if="fileList.length < 8">
                <plus-outlined/>
                <div style="margin-top: 8px">Upload</div>
            </div>
        </a-upload>
        <a-modal :visible="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage"/>
        </a-modal>
    </div>
</template>
<script lang="ts">
    import {PlusOutlined} from '@ant-design/icons-vue';
    import {defineComponent, ref} from 'vue';
    import type {UploadProps} from 'ant-design-vue';

    function getBase64(file: File) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });
    }

    export default defineComponent({
        components: {
            PlusOutlined,
        },
        setup() {
            const previewVisible = ref(false);
            const previewImage = ref('');
            const previewTitle = ref('');

            const fileList = ref<UploadProps['fileList']>([
                // {
                //     uid: '-1',
                //     name: 'image.png',
                //     status: 'done',
                //     url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
                // },
            ]);

            const handleCancel = () => {
                previewVisible.value = false;
                previewTitle.value = '';
            };
            const handlePreview = async (file: any) => {
                if (!file.url && !file.preview) {
                    file.preview = (await getBase64(file.originFileObj)) as string;
                }
                previewImage.value = file.url || file.preview;
                previewVisible.value = true;
                previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
            };

            return {
                previewVisible,
                previewImage,
                fileList,
                handleCancel,
                handlePreview,
                previewTitle,
            };
        },
    });
</script>
<style>
    /* you can make up upload button and sample style by using stylesheets */
    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>