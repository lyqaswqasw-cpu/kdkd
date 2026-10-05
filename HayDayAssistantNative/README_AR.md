# Hay Day Assistant — Android Native

هذا مشروع Android Native حقيقي، وليس HTML.

## الوظائف
- تطبيق APK حقيقي.
- زر عائم فوق التطبيقات باستخدام SYSTEM_ALERT_WINDOW.
- تشغيل وإيقاف المساعد.
- مكان مخصص لاحقاً للمؤقتات والمخزون والاقتراحات.
- لا ينفذ نقرات أو عمليات تلقائية داخل Hay Day ولا يعدّل ملفات اللعبة.

## البناء عبر GitHub Actions
1. ارفع الملفات إلى مستودع GitHub.
2. افتح تبويب Actions.
3. شغّل workflow باسم Build Android APK.
4. بعد انتهاء البناء افتح Artifacts وحمّل HayDayAssistant-debug.
5. فك الضغط وستجد app-debug.apk.

## البناء من Termux
بعد تثبيت Java وGit وGradle/Android SDK المناسب:
./gradlew assembleDebug

ملاحظة أمنية:
لا تضع GitHub Personal Access Token داخل أمر git أو ملف المشروع. استخدم GitHub CLI (`gh auth login`) أو SSH.