# AWS Secret Manager 이용하기. 

## 우선 아래 설정이 필요하다. 

```
aws configure

```

## Maven dependency 추가히기 

```
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-secretsmanager</artifactId>
    <version>1.11.549</version>
</dependency>
```

## AWS 콘솔 이용하여 secret manager 로 시크릿 저장하기. 

AWS Console > AWS Secret Manager > 보안암호 

`새 보안 암호 저장` 를 선택한다. 


