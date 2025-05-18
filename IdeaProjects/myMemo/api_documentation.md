# 备忘录应用 API 文档

## 基础信息
- 基础URL: `/api`
- API根地址: `http://localhost:8080`
- 完整API地址: `http://localhost:8080/api`
- 响应格式: JSON
- 认证方式: Token (在Header中通过 `Authorization: Bearer {token}` 传递)
- 跨域支持: 已启用CORS，支持跨域请求

## 通用响应格式

### 成功响应
```json
{
  "code": 200,          // 状态码，200表示成功
  "message": "success", // 状态描述
  "data": {}            // 返回的数据
}
```

### 错误响应
```json
{
  "code": 400,                     // HTTP状态码
  "message": "参数错误",            // 错误描述
  "data": {                        // 错误详情(可选)
    "field": "参数字段不符合要求"
  }
}
```

## 接口列表

### 1. 用户认证

#### 1.1 用户登录
- **URL**: `/api/auth/login`
- **方法**: POST
- **说明**: 用户登录并获取认证令牌
- **请求参数**:
```json
{
  "username": "user@example.com", // 用户名或邮箱
  "password": "password123",      // 密码
  "rememberMe": true              // 是否记住登录状态
}
```
- **成功响应**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 1,
    "username": "user@example.com",
    "expiresIn": 86400 // token有效期，单位秒
  }
}
```
- **失败响应**:
```json
{
  "code": 401,
  "message": "用户名或密码错误",
  "data": null
}
```

#### 1.2 用户注册
- **URL**: `/api/auth/register`
- **方法**: POST
- **说明**: 新用户注册
- **请求参数**:
```json
{
  "username": "user@example.com", // 用户名或邮箱
  "password": "password123",      // 密码
  "confirmPassword": "password123", // 确认密码
  "agreeTerms": true              // 是否同意用户协议
}
```
- **成功响应**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "userId": 1,
    "username": "user@example.com"
  }
}
```
- **失败响应**:
```json
{
  "code": 400,
  "message": "注册失败",
  "data": {
    "username": "用户名已存在",
    "password": "密码不符合要求"
  }
}
```

#### 1.3 退出登录
- **URL**: `/api/auth/logout`
- **方法**: POST
- **说明**: 用户登出，使当前token失效
- **请求参数**: 无需请求体，通过Authorization头传递token
- **成功响应**:
```json
{
  "code": 200,
  "message": "退出成功",
  "data": null
}
```
- **失败响应**:
```json
{
  "code": 401,
  "message": "未授权或token已过期",
  "data": null
}
```

#### 1.4 忘记密码
- **URL**: `/api/auth/forgot-password`
- **方法**: POST
- **说明**: 发送密码重置邮件
- **请求参数**:
```json
{
  "email": "user@example.com" // 用户邮箱
}
```
- **成功响应**:
```json
{
  "code": 200,
  "message": "重置链接已发送到邮箱",
  "data": null
}
```
- **失败响应**:
```json
{
  "code": 400,
  "message": "邮箱不存在",
  "data": null
}
```

### 2. 备忘录管理

#### 2.1 获取备忘录列表
- **URL**: `/api/memo/list`
- **方法**: GET
- **说明**: 获取当前用户的所有备忘录
- **请求参数**: 通过URL参数传递
    - `page`: 页码，默认1
    - `limit`: 每页数量，默认20
    - `keyword`: 搜索关键词(可选)
    - `sortBy`: 排序字段(可选，默认创建时间)
    - `sortOrder`: 排序方式(可选，"asc"或"desc"，默认"desc")
- **成功响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 100,
    "page": 1,
    "limit": 20,
    "list": [
      {
        "id": 1,
        "title": "会议记录",
        "content": "1. 项目进度讨论\n2. 新功能规划...",
        "createdAt": "2023-06-15T08:30:00Z",
        "updatedAt": "2023-06-15T10:20:00Z"
      },
      {
        "id": 2,
        "title": "购物清单",
        "content": "- 牛奶\n- 鸡蛋\n- 面包...",
        "createdAt": "2023-06-14T14:20:00Z",
        "updatedAt": "2023-06-14T14:20:00Z"
      }
      // 更多备忘录...
    ]
  }
}
```
- **失败响应**:
```json
{
  "code": 401,
  "message": "未授权或token已过期",
  "data": null
}
```

#### 2.2 获取备忘录详情
- **URL**: `/api/memo/detail/{id}`
- **方法**: GET
- **说明**: 获取指定ID的备忘录详情
- **请求参数**: 路径参数 `id`
- **成功响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "会议记录",
    "content": "1. 项目进度讨论\n2. 新功能规划\n3. 下周工作安排\n\n下次会议时间：周五下午2点",
    "createdAt": "2023-06-15T08:30:00Z",
    "updatedAt": "2023-06-15T10:20:00Z"
  }
}
```
- **失败响应**:
```json
{
  "code": 404,
  "message": "备忘录不存在",
  "data": null
}
```

#### 2.3 创建备忘录
- **URL**: `/api/memo/create`
- **方法**: POST
- **说明**: 创建新的备忘录
- **请求参数**:
```json
{
  "title": "新备忘录",      // 备忘录标题
  "content": "备忘录内容..." // 备忘录内容
}
```
- **成功响应**:
```json
{
  "code": 200,
  "message": "创建成功",
  "data": {
    "id": 3,
    "title": "新备忘录",
    "content": "备忘录内容...",
    "createdAt": "2023-06-16T09:15:00Z",
    "updatedAt": "2023-06-16T09:15:00Z"
  }
}
```
- **失败响应**:
```json
{
  "code": 400,
  "message": "创建失败",
  "data": {
    "title": "标题不能为空"
  }
}
```

#### 2.4 更新备忘录
- **URL**: `/api/memo/update/{id}`
- **方法**: PUT
- **说明**: 更新指定ID的备忘录
- **请求参数**:
```json
{
  "title": "更新的标题",      // 备忘录标题
  "content": "更新的内容..." // 备忘录内容
}
```
- **成功响应**:
```json
{
  "code": 200,
  "message": "更新成功",
  "data": {
    "id": 1,
    "title": "更新的标题",
    "content": "更新的内容...",
    "createdAt": "2023-06-15T08:30:00Z",
    "updatedAt": "2023-06-16T11:25:00Z"
  }
}
```
- **失败响应**:
```json
{
  "code": 404,
  "message": "备忘录不存在",
  "data": null
}
```

#### 2.5 删除备忘录
- **URL**: `/api/memo/delete/{id}`
- **方法**: DELETE
- **说明**: 删除指定ID的备忘录
- **请求参数**: 路径参数 `id`
- **成功响应**:
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null
}
```
- **失败响应**:
```json
{
  "code": 404,
  "message": "备忘录不存在",
  "data": null
}
```

#### 2.6 搜索备忘录
- **URL**: `/api/memo/search`
- **方法**: GET
- **说明**: 根据关键词搜索备忘录
- **请求参数**: 通过URL参数传递
    - `keyword`: 搜索关键词
    - `page`: 页码，默认1
    - `limit`: 每页数量，默认20
- **成功响应**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total": 5,
    "page": 1,
    "limit": 20,
    "list": [
      {
        "id": 1,
        "title": "会议记录",
        "content": "1. 项目进度讨论\n2. 新功能规划...",
        "createdAt": "2023-06-15T08:30:00Z",
        "updatedAt": "2023-06-15T10:20:00Z"
      },
      // 更多搜索结果...
    ]
  }
}
```
- **失败响应**:
```json
{
  "code": 400,
  "message": "搜索参数错误",
  "data": {
    "keyword": "关键词不能为空"
  }
}
```

## 数据模型

### 用户模型
```
xg_user {
  id: 整数,             // 用户ID
  user_name: 字符串,     // 用户名/邮箱
  password: 字符串,     // 密码（加密存储）
  gmt_create: 日期时间,  // 创建时间
  gmt_modified: 日期时间   // 更新时间
}
```

### 备忘录模型
```
xg_memo {
  id: 整数,             // 备忘录ID
  user_id: 整数,         // 所属用户ID
  title: 字符串,        // 备忘录标题
  content: 文本,        // 备忘录内容
  gmt_create: 日期时间,  // 创建时间
  gmt_modified: 日期时间   // 更新时间
}
```

## 注意事项

1. 所有API均需进行用户认证，除了登录、注册和忘记密码接口
2. 用户只能访问和操作自己的备忘录
3. 所有时间均使用ISO 8601格式的UTC时间
4. 对于敏感操作（如删除）建议添加二次确认机制

## 跨域访问支持

本API服务已配置支持跨域资源共享(CORS)，允许前端从不同域名访问API。

### CORS配置详情

- 允许的来源: `*` (允许所有来源，生产环境建议指定具体域名)
- 允许的方法: `GET, POST, PUT, DELETE, OPTIONS`
- 允许的头信息: `Authorization, Content-Type, Accept`
- 是否允许携带凭证: `true`
- 预检请求缓存时间: `3600` 秒

### 前端示例

前端开发人员可以直接发起跨域请求，无需额外配置。例如:

```javascript
// 使用fetch API发起跨域请求示例
fetch('http://localhost:8080/api/memo/list', {
  method: 'GET',
  headers: {
    'Authorization': 'Bearer [your_token]',
    'Content-Type': 'application/json'
  },
  credentials: 'include' // 如需发送cookies
})
.then(response => response.json())
.then(data => console.log(data))
.catch(error => console.error('Error:', error));
```