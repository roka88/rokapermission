# rokapermission
안드로이드 6.0+ 이상 퍼미션 허가를 위한 라이브러리
간단한 퍼미션 체킹 방식

![myimage-alt-tag](http://lycle.co.kr/images/t_1475674282616_user_event.png)

#Gradle 

<pre>
repositories {
    maven { url "https://jitpack.io" }
}
</pre>

<pre>
dependencies {
    compile 'com.github.roka88:rokapermission:0.0.1'
}
</pre>

#사용법

<pre>
RokaPermission.Init(mActivity)
              .permission(Manifest.permission.SEND_SMS)
              .setGrantedListener(mPermissionGrantedListener)
              .setDeniedListener(mPermissionDeniedListener)
              .setAplicationSetting("설정", "취소", "문자를 전송하시려면 동의해주셔야 합니다.")
              .start();
</pre>


<pre>
RokaPermission.Init(mActivity)
              .permission(Manifest.permission.SEND_SMS, Manifest.permission.CAMERA)
              .setGrantedListener(mPermissionGrantedListener)
              .setAplicationSetting("setting", "cancel", "you should ...")
              .start();
</pre>

#CallBackListener

<pre>
PermissionGrantedListener mPermissionGrantedListener = (ArrayList<xmp><String></xmp> arrayList) -> {
    //TODO : 허가된 퍼미션을 콜백
    // 행동
};
PermissionDeniedListener mPermissionDeniedListener = (ArrayList<xmp><String></xmp> arrayList) -> {
   //TODO : 거부된 퍼미션을 콜백
   // 행동
};
</pre>



#참고
박상권님의 https://github.com/ParkSangGwon/TedPermission 에서 영감을 얻었습니다.

#License
<pre>
Copyright 2016 Roka

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
