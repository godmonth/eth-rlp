# eth-rlp
rlp解码，暂时只有解码
需要Java 8,因为用到了Byte.toUnsignedInt，如果需要jdk8一下，自己分支出去

maven dependency
```

		<dependency>
			<groupId>com.godmonth.eth</groupId>
			<artifactId>eth-rlp</artifactId>
			<version>0.1</version>
		</dependency>


rlp decoder ,only decode
java 8 required for Byte.toUnsignedInt. If you need low version of jdk,just branch it. 