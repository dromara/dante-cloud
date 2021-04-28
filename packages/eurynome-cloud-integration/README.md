

# 一、braineex-integration 使用说明

## 背景说明

经过对Braineex后台代码的梳理，发现系统集成了很多第三方技术输出内容。原有第三集成代码，主要分布在braineex-common-base包中，以独立服务的方式运行，并通过feign对其它服务提供接口支持。

本身这种使用方式没有任何问题，但是基于Braineex后台代码本身，存在以下问题：

1. Pigx框架提供了很多无用的、额外的服务和代码，加之本身各个包的依赖关系并没有很好地梳理，导致系统各个模块间依赖关系混乱，IDE开启和编译都需要更长的时间。
2. Braineex后台代码经过多个人编写，编写风格差异较大，相同的作用的第三方集成代码，存在多份分布在不同的包和服务中，存在大量无用或者重复的代码，braineex-common-base公共服务的作用并没有达到预定目标，反而系统运行时需要额外多运行一个服务，增加运行维护管理成本。
3. 已有的第三方集成代码，并没有严格分析第三方接口的参数和返回值对象构成，没有进行实体的封装来进行数据转换。导致存在大量的JSON解析的代码，编写风格也不统一。这种方式本身没有问题，但是由于第三方应用数据传递和数据返回复杂度各异，使用方式也不统一，新来人员接手相关代码，仅通过读代码是很难清楚业务实现逻辑的，还需要重新分析第三方应用集成的文档，才能明白具体代码的逻辑。这无形中增加大量的维护难度和成本投入。
4. 第三方应用的集成需要有很多配置信息，但现有后台代码并没有以统一风格存放和保存这些信息：要么写死在代码中，要么使用了不同格式的配置文件放在不同的包中。写代码最忌讳的事情就是修改同一个东西要导出修改，这也意味着会有多个点会出现问题。

## 重构说明

基于以上的考虑，所以重构出现了braineex-integration包，该包涵盖除了支付以外的所有第三方应用集成代码，具体优势变现在：

- 全部采用实体映射转换的方式进行数据交换，一方面保证交互数据尽可能的全面，而不是单一的从某个JSON中解析个别字段，方便后续应用的扩展；另一方面，新人接受相关代码不再需要详细翻阅第三方集成的文档，就可以直接了解代码，进行修改或维护。
- 全部采用@EnableXXX的方式，进行开启和使用，无须再额外运行一个独立服务，导出使用feign进行调用。
- 全部采用yml格式进行参数配置，未来可以统一存放在Nacos配置中心，便于维护管理。也可以利用jasypt对关键配置信息加密



## 集成说明

### （1）阿里云短信

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableAliyunSms
```

**配置参数**

```yaml
braineex:
  integration:
    aliyun:
      access-key-id: LTAIbpBuZTocddot
      access-key-secret: qqTHQOJRlc7BSqPJMjHnftGH4vmW2W
      region-id: cn-shanghai
      uid: 1680216538418745
      seed: 9b73200d8b30ad89dba7cb7e37282c29
      sms:
        sign-name: braineex
        version: 2017-05-25
        default-template-id: VERIFICATION_CODE
        templates: { "VERIFICATION_CODE": "SMS_180052229" } #可以支持多个短信模版
```



> 阿里云短信最新代码已经集成至现有系统中并替换已有业务代码，目前注解和参数配置在braineex-persion-biz服务中。



### （2）环信

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableEasemob
```

**配置参数**

```yaml
braineex:
  integration:
    social:
      easemob:
        url: http://a1.easemob.com/1131190611046482/braineex
        client-id: YXA6rP4qUIv0EemF4THv1mHhfQ
        client-secret: YXA6hqKIZ0Z8xXn8XGUbtLWQ1vWNEUo
```



> 环信信最新代码已经集成至现有系统中并替换已有业务代码，目前注解和参数配置在braineex-persion-biz服务中。



### （3）微信小程序

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableWxapp
```

**配置参数**

```yaml
braineex:
  integration:
    social:
      wxapp:
        default-app-id: wxff1142afea12b9b8
        configs:
          - app-id: wxff1142afea12b9b8
            secret: d3fa3ea106413dce613cdc480453271e
            token: #微信小程序消息服务器配置的token
            aes-key: #微信小程序消息服务器配置的EncodingAESKey
            messageDataFormat: JSON
          - app-id: wxb677abefc40a9943
            secret: a514258114f67380ffc56589309cc7e9
            token: #微信小程序消息服务器配置的token
            aes-key: #微信小程序消息服务器配置的EncodingAESKey
            messageDataFormat: JSON
        subscribes:
          - message-type: SM_DEMO
            template-id: sdfsdfsdfsd
            redirect-page: /page/index/index
          - message-type: SM_sdfsdfsdf
            template-id: s1111
            redirect-page: /page/index/index

```



>  微信最新代码已经集成至现有系统中并替换已有业务代码，目前注解配置在braineex-integration-rest中，后面会具体说明。参数配置在braineex-admin-biz服务中。



#### 微信小程序订阅消息

braineex-integration已经集成了微信小程序发送的代码，由于订阅消息的需求暂时没有确定，具体模版还没有申请下来，所以还没有集成至具体的业务代码中。一个小程序可能会使用多个订阅消息的模版，因此这里订阅消息模版的实现，采用了可以支持扩展的方式。

现有代码的订阅消息实现，采用的是基于Spring Boot的工厂模式。如果要新增加订阅消息的功能，参考以下步骤：

1. 编写模版信息处理类，编写一个类，继承抽象类`SubscribeMessageHandler`。这个类就是构造订阅消息的模版，填充具体的信息。
2. 在这个信息处理类上增加`@Component`或`@Service`注解，注意注解上一定要定义一个名字。这个名字就是代码中的`subscribeId`，同时这个名字也要配置在yml文件中。例如：

```yaml
       subscribes:
          - message-type: SM_DEMO
            template-id: sdfsdfsdfsd
            redirect-page: /page/index/index
```

3. 前台调用`/integration/rest/wxapp/sendSubscribeMessage`这个接口发送订阅消息。传递的`subscribeId`不同，就可以发送不同的订阅消息。

### （4）微信公众号

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EanbleWxmpp
```

**配置参数**

```yaml
braineex:
  integration:
    social:
      wxmpp:
        use-redis: false
        redis:
          host: 127.0.0.1
          port: 6379
        configs:
          - appId: 1111 # 第一个公众号的appid
            secret: 1111 # 公众号的appsecret
            token: 111 # 接口配置里的Token值
            aesKey: 111 # 接口配置里的EncodingAESKey值
          - appId: 2222 # 第二个公众号的appid，以下同上
            secret: 1111
            token: 111
            aesKey: 111
```



> 由于braineex中没有微信公众号相关的业务代码，因此目前只把核心配置完成，后续如果要开发微信公众号的功能，开启相应的注解，开发具体功能即可。

### （5）IOS Apns

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableApns
```

**配置参数**

```yaml
braineex:
  integration:
    push:
      apns:
        sandbox: true
        file-path: classpath:/certification/ios/apnsdev.p12
        password: 123456
        bundle-id: com.braineex.brainex2test
```



> IOS Apns是苹果专有的消息推送渠道。各个品牌的手机特别是Android都有自己的消息推送通道。第三方消息推送产品，大多也是利用各个品牌的消息推送渠道推送消息。目前集成的Apns代码，只支持单机的推送。

### （6）极光推送

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableJPush
```

**配置参数**

```yaml
braineex:
  integration:
    push:
      jpush:
        app-key: 730642aa1b34
        master-secret: 01b5e08a9c
```



> 注意：目前公司还没有注册极光账号，如需使用需要注册
>
> 极光推送基本的推送代码以及设备管理代码应集成完毕，可以直接使用。

### （7） 百度OCR

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableBaiduOcr	
```

**配置参数**

```yaml
braineex:
  integration:
    content:
      baidu:
        app-id: 18037077
        app-key: VDU8fkbXQ07RFvNIORVOruHz
        secret-key: jnYlk0ExwWGrwgqegO0rOOVAVss1uf43
```



> 百度OCR营业执照识别、身份证势必最新代码已经集成至现有系统中并替换已有业务代码，目前注解和参数配置在braineex-persion-biz服务中。



### （8）天眼查

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableTianyan
```

**配置参数**

```yaml
braineex:
  integration:
    content:
      tianyan:
        token: 4836348a-c348-4232-86b7-cd2da3d22215
        base-url: http://open.api.tianyancha.com/services/open
```



> 天眼查最新代码已经集成至现有系统中并替换已有业务代码，目前注解和参数配置在braineex-persion-biz服务中。



### （9） 阿里云OSS

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableAliyunOss
```

**配置参数**

```yaml
braineex:
  integration:
    aliyun:
      access-key-id: LTAIbpBuZTocddot
      access-key-secret: qqTHQOJRlc7BSqPJMjHnftGH4vmW2W
      region-id: cn-shanghai
      oss:
        endpoint: oss-cn-shenzhen.aliyuncs.com
        bucket-name: braineex
        base-url: http://braineex.oss-cn-shenzhen.aliyuncs.com
        picture-types: .GIF,.JPEG,.JPG,.PNG,.TGA,.TIF,.EXIF,.Webp,.BMP,.PCX,.FPX,.SVG,.PSD,.CDR,.PCD,.DXF,.UFO,.EPS,.AI,.HDRI,.RAW,.WMF,.FLIC,.EMF,.ICO,.FLA
        video-types: .AVI,.WMV,.MPEG,.MP4,.MOV,.MKV,.FLV,.F4V,.M4V,.RMVB,.RM,.3GP,.DAT,.TS,.MTS,.VOB,.SWF
        voice-types: .MP3,.OGG,.WAV,.APE,.CDA,.AU,.MIDI,.MAC,.AAC,.FLAC
        app-types: .APK,.IPA,.EXE
        doc-types: .PDF,.DOC,.DOCX,.XLS,.XLSX,.PPT,.PPTX,.WPS,.RAR,.ZIP,.TXT,.DDB,.DWG
```



> 阿里云OSS最新代码已经集成至现有系统中并替换已有业务代码，目前注解配置在braineex-integration-rest中，后面会具体说明。参数配置在braineex-admin-biz服务中。



### （10） 阿里云内容审核

只需要依赖braineex-integration包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableAliyunScan
```

**配置参数**

```yaml
braineex:
  integration:
    aliyun:
      access-key-id: LTAIbpBuZTocddot
      access-key-secret: qqTHQOJRlc7BSqPJMjHnftGH4vmW2W
      region-id: cn-shanghai
      uid: 1680216538418745
      seed: 9b73200d8b30ad89dba7cb7e37282c29
      scan:
        # 注意：这个url需要根据不同的部署环境，进行修改。
        # 目前系统还没有使用多环境配置，等使用多环境配置的时候，可以将该参数进行动态配置。
        callback-base-url: https://server.braineex.com/server/admin/integration/open/aliyun
        image:
          connect-timeout: 3000
          read-timeout: 10000
        video:
          connect-timeout: 3000
          read-timeout: 10000
        text:
          connect-timeout: 3000
          read-timeout: 6000
        voice:
          connect-timeout: 3000
          read-timeout: 6000
```



> 阿里云内容最新代码已经集成至现有系统中并替换已有业务代码，目前注解和参数配置在braineex-persion-biz和braineex-release-biz服务中。

# 二、braineex-integration-rest 使用说明

## 背景说明

braineex-integration包中，已经包含第三方集成的通用代码，理论上确实没有必要再重新建一个braineex-integration-rest包。但在实际应用中，除了通用的基础代码外，很多常用的接口也可以写为通用接口（比如说，微信小程序的登录、微信小程序内容审核、阿里云文件上传等），客户端可以接直接调用使用，这样就不需要再反复创建这个接口。

braineex-integration-rest包的作用就是将非常通用的REST接口，进行统一管理和封装，今后不管做什么APP或小程序都可以不用修改任何代码就拿来直接使用。

## 集成说明

### （1）微信小程序

只需要依赖braineex-integration-rest包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```java
@EnableWxappRest
```

**配置参数**

由于微信小程序接口，需要依赖braineex-integration中的服务，`@EnableWxappRest`  注解会帮助开启`@EnableWxapp`注解， 因此使用该注解同样需要配置参数。

```yaml
braineex:
  integration:
    social:
      wxapp:
        default-app-id: wxff1142afea12b9b8
        configs:
          - app-id: wxff1142afea12b9b8
            secret: d3fa3ea106413dce613cdc480453271e
            token: #微信小程序消息服务器配置的token
            aes-key: #微信小程序消息服务器配置的EncodingAESKey
            messageDataFormat: JSON
          - app-id: wxb677abefc40a9943
            secret: a514258114f67380ffc56589309cc7e9
            token: #微信小程序消息服务器配置的token
            aes-key: #微信小程序消息服务器配置的EncodingAESKey
            messageDataFormat: JSON
        subscribes:
          - message-type: SM_DEMO
            template-id: sdfsdfsdfsd
            redirect-page: /page/index/index
          - message-type: SM_sdfsdfsdf
            template-id: s1111
            redirect-page: /page/index/index
```

**启用接口**

开启该注解后，可以直接使用以下接口：

```yaml
/integration/open/wxapp/login #微信小程序登录
/integration/rest/wxapp/sendSubscribeMessage #发送订阅消息
/integration/rest/wxapp/checkMessage #检查文本内容合规性
/integration/rest/wxapp/checkImage #检查图片内容合规性
```



> 该部分最新代码已经集成至现有系统中并替换已有业务代码，目前注解和参数配置在braineex-admin-biz服务中。



### （2） 阿里云

只需要依赖braineex-integration-rest包，并在使用的服务中，配置下面的注解以及参数就可以使用：

**注解**

```yaml
@EnabalAliyunRest
```

**配置参数**

由于阿里云接口，需要依赖braineex-integration中的OSS服务，`@EnableAliyunRest`  注解会帮助开启`@EnableAliyunOss`注解， 因此使用该注解同样需要配置参数。

> 由于阿里云是一体化的设计，阿里云OSS、阿里云短信、阿里云内容审核，不管用哪个服务，都需要配置accessKeyId，accessKeySecret，regionId三个参数。因此，阿里云相关应用的配置参数设计，没有按照其它第三方应用的方式

```yaml
braineex:
  integration:
    aliyun:
      access-key-id: LTAIbpBuZTocddot
      access-key-secret: qqTHQOJRlc7BSqPJMjHnftGH4vmW2W
      region-id: cn-shanghai
      uid: 1680216538418745
      seed: 9b73200d8b30ad89dba7cb7e37282c29
      scan:
        # 注意：这个url需要根据不同的部署环境，进行修改。
        # 目前系统还没有使用多环境配置，等使用多环境配置的时候，可以将该参数进行动态配置。
        callback-base-url: https://server.braineex.com/server/admin/integration/open/aliyun
        image:
          connect-timeout: 3000
          read-timeout: 10000
        video:
          connect-timeout: 3000
          read-timeout: 10000
        text:
          connect-timeout: 3000
          read-timeout: 6000
        voice:
          connect-timeout: 3000
          read-timeout: 6000
      oss:
        endpoint: oss-cn-shenzhen.aliyuncs.com
        bucket-name: braineex
        base-url: http://braineex.oss-cn-shenzhen.aliyuncs.com
        picture-types: .GIF,.JPEG,.JPG,.PNG,.TGA,.TIF,.EXIF,.Webp,.BMP,.PCX,.FPX,.SVG,.PSD,.CDR,.PCD,.DXF,.UFO,.EPS,.AI,.HDRI,.RAW,.WMF,.FLIC,.EMF,.ICO,.FLA
        video-types: .AVI,.WMV,.MPEG,.MP4,.MOV,.MKV,.FLV,.F4V,.M4V,.RMVB,.RM,.3GP,.DAT,.TS,.MTS,.VOB,.SWF
        voice-types: .MP3,.OGG,.WAV,.APE,.CDA,.AU,.MIDI,.MAC,.AAC,.FLAC
        app-types: .APK,.IPA,.EXE
        doc-types: .PDF,.DOC,.DOCX,.XLS,.XLSX,.PPT,.PPTX,.WPS,.RAR,.ZIP,.TXT,.DDB,.DWG
```

**启用接口**

```yaml
/integration/rest/aliyun/oss/file # 上传单个文件
/integration/rest/aliyun/oss/files # 上传单个文件
/integration/open/aliyun/video/callback # 阿里云视频异步审核回调接口
/integration/open/aliyun/image/callback # 阿里云图片异步审核回调接口
```

#### 额外说明

**（1）阿里异步审核问题**

阿里云内容审核，如果是采用异步的方式审核，那么对于结果的处理可以采用“轮询”或“Callback”两种方式。

原有Braineex代码，采用的是“轮询”方式，但不是真正的“轮询”，而是通过消息队列设置一定的延时时间，根据这个时间定时获取。这种情况下，大部分情况可以满足，一旦出现某个内容审核时间很长，会就出现问题。如果将这个时间设置的过长，可能会影响应用的响应效果。

采用Callback的方式，相对更加合理。阿里内容审核完成，会主动向callback推送结果。避免设定时间的问题。但是该种方式也存在一定的问题：

1. 需要对外开放接口，虽然阿里提供了uid + seed + content的加密方式，但是也存在一旦接口被暴露变为攻击的对象。
2. 阿里自身的机制，如果callback接口执行出错，会对该接口进行反复请求，直到运行成功或最多16次的调用。这样也会对系统造成更多的压力。

解决思路：

1. 每次创建异步内容审核任务，最好独立生成不同的seed（目前是写死在配置中），将这个seed存储在redis中，在验证数据有效性的时候，从redis读取使用。一旦验证成功，就将该seed删除。这样可以增加接口的安全性。
2. 增加阿里callback接口的幂等控制，降低接口受到“轰炸”请求后对整个系统的影响。
3. callback等对外开放的接口，最好以单独服务的形式独立部署，并且与其它服务隔离。这样即使callback服务受到攻击，也可以避免对整个系统的影响。

**（2）Braineex存在的问题**

目前Braineex后台系统，没有采用多环境配置的设置，（即，不同部署环境可以采用不同的配置），这样就导致在不同环境下，只能手动修改一些配置。手动修改配置，一旦忘记或者遗漏，就会产生不必要的错误，增加运维的复杂度和投入。比如，测试环境采用的域名是`server.braineex.com`而生产环境采用的域名是`app.braineex.com`。如果要使用阿里内容审核callback模式，就要在不同的环境下手动修改这个域名，才能保证callback正常运行。

# 三、日志中心集成

日志中心是微服务架构的重要组成部分，通过日志中心就可以解决每次查看日志都一个一个服务的去查看的问题。虽然，现在已经搭建了日志中心的基础架构，但是一直没有使用起来。

近日，编写了一些代码将Braineex后台系统，与已有的日志中心进行了整合，并在`gateway`服务中验证成功。

**注解**

```java
@EnableBraineexLogstash
```

**配置参数**

```yaml
braineex:
  platform:
    logstash:
      server-address: 192.168.0.171:5044
      loggers:
        com.braineex: debug
        org.springframework: info
```

**说明**

目前使用的日志收集方式，是利用`logstash-logback-encoder`包来实现。这个包是采用TCP的方式，向logstash发送数据。因此，logstash必须开放`5044`这个端口。然后用这个端口进行输入输出的配置。需要在logstash中增加下面的配置：

```json
input {
    tcp {
        port => 5044
        codec => json_lines
    }

}
output{
    elasticsearch {
        hosts => ["localhost:9200"]
    }
}
```

**使用方法**

1. 在Nacos对应服务的配置中，增加logstash的配置。
2. 在对应服务上开启`@EnableBraineexLogstash`注解。

**存在问题**

目前`@EnableBraineexLogstash`可以正常使用，但是启动的时机略晚，后续可以根据实际需求，调整`@EnableBraineexLogstash`对应config类的启动时机。

# 四、微信小程序手机号码登录

Pigx框架本身支持手机号码、微信小程序以及其它的第三方登录，均是以传递code的方式，进行统一处理。这个应该是符合逻辑的，很多第三方登录都是使用OAuth2的authorization code模式，传递code没问题；微信小程序登录也是通过前端传递一个code进行登录。

而最新的小程序登录要使用从微信小程序端获取手机号码的方式进行登录，这种方式就需要后端代码到微信端解析手机号码，再进行braineex的注册等先关操作。要从微信端解析手机号码，就要前端多传几个参数，这就导致Pigx现有的小程序登录不满足需求，而进行了大改。

到底是前端采用原有方式登录，增加一个页面进行手机号绑定方便，还是后端由于多几个参数就要投入大量精力和时间修改平台方便？这一类需求我个人觉得还是要慎重考虑。



目前，微信小程序手机号码登录，以及实现并且部署上线。核心逻辑在`pigx-common-security`包中，具体代码在：`com.pig4cloud.pigx.common.security.miniapp`中。

**出现问题怎么解决**

目前，微信小程序手机号码登录没有问题。如果换到其它小程序出现问题，一定是在微信端解析手机号码出现问题。这个问题主要是由于传统的登录操作都会使用`application/x-www-form-urlencoded` 进行提交。这种模式下，会对参数进行urlencoded操作。

而解析微信小程序手机号码，所需的`encryptedData`、`iv`两个参数中，会存在特殊的字符，这在`application/x-www-form-urlencoded` 模式下会被转义，因此就导致无法解密手机号码而抛错。所以这里前端传输数据一定要用`application/json`模式