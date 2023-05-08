<template>
    <div class="loginContainer">
        <h3 class="loginTitle">品优购后台管理系统</h3>
        <a-form
                :model="user"
                name="basic"
                :label-col="{ span: 8 }"
                :wrapper-col="{ span: 16 }"
                autocomplete="off"
        >
            <a-form-item
                    label="用户名"
                    name="username"
            >
                <a-input v-model:value="user.username"/>
            </a-form-item>

            <a-form-item
                    label="密码"
                    name="password"
            >
                <a-input-password v-model:value="user.password"/>
            </a-form-item>

            <a-form-item name="remember" :wrapper-col="{ offset: 8, span: 16 }">
                <a-checkbox v-model:checked="checked">自动登录</a-checkbox>
            </a-form-item>

            <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
                <a-button type="primary" html-type="submit" class="loginRemember">登录</a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import {defineComponent, ref,} from 'vue';

    export default defineComponent({
        name: "Login",

        setup() {
            const user = ref({})
            return {
                user,
            }
        }
    })

</script>

<style scoped>
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 188px auto;
        width: 450px;
        padding: 15px 35px 15px 10px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 0px auto 20px auto;
        text-align: center;
    }

    .loginRemember {
        text-align: left;
        margin: 0px 0px 15px 0px;
    }
</style>