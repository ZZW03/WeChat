<script setup>
import {Check, Document} from "@element-plus/icons-vue";
import {computed, reactive, ref} from "vue";
import {Delta, Quill, QuillEditor} from "@vueup/vue-quill";
import '@vueup/vue-quill/dist/vue-quill.snow.css';

import ImageResize from "quill-image-resize-vue";
import { ImageExtend, QuillWatch } from "quill-image-super-solution-module";
import {accessHeader, get, post, post1} from "@/net/net";
import axios  from "axios";
import {ElMessage} from "element-plus";
import Dot from "@/views/Components/Dot.vue";

const emit = defineEmits(['close'])
const refEditor = ref()
const prop = defineProps({
  show : Boolean
})

  const editor = reactive({
    type:"",
    title:"",
    text:"",
    types:[]
  })

function initEditor() {
  refEditor.value.setContents("",'user')
  editor.title = ''
  editor.type=null
}

get("/api/forum/TopicType",(data)=>{
  editor.types = data
})
function deltaToText(delta) {
  if(!delta.ops) return ""
  let str = ""
  for (let op of delta.ops)
    str += op.insert
  return str.replace(/\s/g, "")
}


const contentLength = computed(() => deltaToText(editor.text).length)


function submitTopic() {
  const text = deltaToText(editor.text)
  if(text.length > 20000) {
    ElMessage.warning('字数超出限制，无法发布主题！')
    return
  }
  if(!editor.title) {
    ElMessage.warning('请填写标题！')
    return
  }
  if(!editor.type) {
    ElMessage.warning('请选择一个合适的帖子类型！')
    return
  }
  post1('/api/forum/Create-topic', {
    type: editor.type.id,
    title: editor.title,
    content: editor.text
  }, () => {
    ElMessage.success("帖子发表成功！")
    setTimeout(()=>{
      emit('close')
    },500)

  })
}



Quill.register('modules/imageResize', ImageResize)
Quill.register('modules/ImageExtend', ImageExtend)

const editorOption = {
  modules: {
    toolbar: {
      container: [
        "bold", "italic", "underline", "strike","clean",
        {color: []}, {'background': []},
        {size: ["small", false, "large", "huge"]},
        { header: [1, 2, 3, 4, 5, 6, false] },
        {list: "ordered"}, {list: "bullet"}, {align: []},
        "blockquote", "code-block", "link", "image",
        { indent: '-1' }, { indent: '+1' }
      ],
      handlers: {
        'image': function () {
          QuillWatch.emit(this.quill.id)
        }
      }
    },
    imageResize: {
      modules: [ 'Resize', 'DisplaySize' ]
    },
    ImageExtend: {
      action:  axios.defaults.baseURL + '/api/img/img',
      name: 'file',
      size: 5,
      loading: true,
      accept: 'image/png, image/jpeg',
      response: (resp) => {
        if(resp.data.code === 400) {
          ElMessage.warning('图片上传失败，请联系管理员!')
          editor.uploading = false
          return null;
        } else {
          ElMessage.success('图片上传成功!')
          editor.uploading = false
          return axios.defaults.baseURL +  resp.data
        }
      },
      methods: 'POST',
      headers: xhr => {
        xhr.setRequestHeader('Authorization', accessHeader().Authorization);
      },
      start: () => editor.uploading = true,

    }
  }
}
</script>

<template>
  <div>
    <el-drawer
        :model-value="prop.show"
        title="发表帖子"
        :direction="'btt'"
        size="600"
        style="width: 1000px"
        @opened="initEditor"
        @close="emit('close')"
    >
      <template #header >
        <div>
          <h1 style="display: inline-block;font-size: large">请在此处发表帖子</h1>
          <span style="display: block;font-size: small">在此之前,请遵守法律法规,不要做违法之事,不要随意说脏话,不可散播黄色毒品</span>
        </div>
      </template>

      <div style="display: flex;gap: 10px">
        <div style="width: 150px">
          <el-select placeholder="请选择帖子类型"  value-key="id" v-model="editor.type" :disabled="!editor.types.length" >
            <el-option v-for="item in editor.types" :value="item"  :label="item.name">
              <dot :color="item.color" style="display: inline-block;margin-right: 3px"></dot>
              <span>{{item.name}}</span>
            </el-option>
          </el-select>
        </div>
        <div style="flex: 1">
          <el-input v-model="editor.title" placeholder="请输入标题..." :prefix-icon="Document"
          maxlength="10"/>
        </div>
      </div>
      <div style="position: relative;top: 10px;left: 5px;color: #21ff82;font-size: 14px">
        <dot :color="editor.type?.color"></dot>
        <span>{{editor.type? editor.type.desc:'请在上方选择一个帖子类型'}}</span>
      </div>
      <div style="margin-top: 20px;height: 320px" >
        <QuillEditor v-model:content="editor.text"
                     placeholder="你想分享什么呢?"
                     content-type="delta"
                      :options="editorOption"
                     ref="refEditor"
        />
      </div>
      <div style="display: flex;justify-content: space-between;margin-top:50px;">
        <div style="color: grey;font-size: 13px">
          当前字数为{{contentLength}},最多支持500字
        </div>
        <div>
            <el-button type="success" :icon="Check" @click="submitTopic">点击发布</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped>
:deep(.el-drawer){
  margin: auto;
  border-radius: 10px 10px 0 0 ;
}

:deep(.el-drawer__header){
    margin: 0;
}

:deep(.ql-toolbar){
  border-radius: 5px 5px 0 0;
}

:deep(.ql-container){
  border-radius: 0 0 5px 5px;
}

</style>