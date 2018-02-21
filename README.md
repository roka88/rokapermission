# rokapermission
안드로이드 6.0+ 이상 퍼미션 허가를 위한 라이브러리
간단한 퍼미션 체크 및 오버레이 체크와 요청

![myimage-alt-tag](http://file.lycle.co.kr/images/t_1475674282616_user_event.png)

#Gradle

<pre>
repositories {
    maven { url "https://jitpack.io" }
}
</pre>

<pre>
dependencies {
    compile 'com.github.roka88:rokapermission:0.0.4'
}
</pre>

#Version
<pre>
0.0.4 의존성 변경
0.0.3 Android Target SDK 24로 상향
0.0.2 Overlay 권한 요청 추가
0.0.1 초기버전
</pre>


#사용법
<pre>
// 권한 요청
RokaPermission.Init(mActivity)
              .permission(Manifest.permission.SEND_SMS)
              .setGrantedListener(mPermissionGrantedListener)
              .setDeniedListener(mPermissionDeniedListener)
              .setPermissionMsgSetting("설정", "취소", "문자를 전송하시려면 동의해주셔야 합니다.")
              .start();
</pre>


<pre>
// 권한 요청
RokaPermission.Init(mActivity)
              .permission(Manifest.permission.SEND_SMS, Manifest.permission.CAMERA)
              .setGrantedListener(mPermissionGrantedListener)
              .setPermissionMsgSetting("setting", "cancel", "you should ...")
              .start();
</pre>

<pre>
// 오버레이 요청
RokaPermission.Init(this)
              .overlay()
              .setOverlayMsgSetting("설정", "취소", "이 앱은 오버레이 설정이 허가되어야 합니다,")
              .setOverlayGrantedListener(mOverlayGrantedListener)
              .setOverlayDeniedListener(mOverlayDeniedListener)
              .start();
</pre>

#주의사항
<pre>
1. 권한 요청과 오버레이 요청은 동시에 할 수 없음
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
OverlayGrantedListener mOverlayGrantedListener = () -> {
  // TODO : 오버레이 허가시
};

OverlayDeniedListener mOverlayDeniedListener = () -> {
  // TODO : 오버레이 거부시
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
