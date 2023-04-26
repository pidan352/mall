<template>
    <a-tooltip :trigger="['focus']" placement="topLeft" overlay-class-name="numeric-input">
        <template v-if="inputValue" #title>
      <span class="numeric-input-title">
        {{ formatValue }}
      </span>
        </template>

        <a-input
                v-model:value="brandObject.sortOrder"
                placeholder="Input a number"
                :max-length="25"
                style="width: 120px"
                @blur="onBlur"
        />
    </a-tooltip>
</template>
<script lang="ts">
    import {computed, defineComponent, reactive, ref, watch} from 'vue';

    const brandObject = reactive<Record<string, any>>({})

    function formatNumber(value: string) {
        value += '';
        const list = value.split('.');
        const prefix = list[0].charAt(0) === '-' ? '-' : '';
        let num = prefix ? list[0].slice(1) : list[0];
        let result = '';

        while (num.length > 3) {
            result = `,${num.slice(-3)}${result}`;
            num = num.slice(0, num.length - 3);
        }

        if (num) {
            result = num + result;
        }

        return `${prefix}${result}${list[1] ? `.${list[1]}` : ''}`;
    }

    export default defineComponent({
        setup() {
            const formatValue = computed(() => {
                if (brandObject.sortOrder === '-') return '-';
                return formatNumber(brandObject.sortOrder);
            });

            const format = (val: any, preVal: any) => {
                const reg = /^-?\d*(\.\d*)?$/;

                if ((!isNaN(+val) && reg.test(val)) || val === '' || val === '-') {
                    brandObject.sortOrder.value = val;
                } else {
                    brandObject.sortOrder.value = preVal;
                }
            };

            // '.' at the end or only '-' in the input box.
            const onBlur = () => {
                if (
                    brandObject.sortOrder.charAt(brandObject.sortOrder.length - 1) === '.' ||
                    brandObject.sortOrder === '-'
                ) {
                    format(brandObject.sortOrder.slice(0, -1), brandObject.sortOrder);
                }
            };

            watch(brandObject.sortOrder, (val, preVal) => {
                format(val, preVal);
            });

            return {
                // inputValue,
                onBlur,
                formatValue,
                brandObject,
            };
        },
    });
</script>
<style>
    /* to prevent the arrow overflow the popup container,
    or the height is not enough when content is empty */
    .numeric-input .ant-tooltip-inner {
        min-width: 32px;
        min-height: 37px;
    }

    .numeric-input .numeric-input-title {
        font-size: 14px;
    }
</style>