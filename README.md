# Family GPS Tracker — v1.0.0

Android starter project with a playful family icon, Vietnamese prototype UI, location permission/status checks, member/admin screen mockups, and GitHub Actions APK build.

## Important status
This is a **UI/permission MVP**, not yet a production family location-sharing service. The member cards and admin actions are sample UI. Location is not uploaded or shared between phones because no authenticated backend is configured. Do not use this build for real tracking.

## Build APK
1. Create a GitHub repository and upload all files.
2. Push to `main` or `master`, or open **Actions → Build Family GPS Tracker APK → Run workflow**.
3. Download artifact `FamilyGPSTracker-v1.0.0-debug`.
4. APK output name: `FamilyGPSTracker-v1.0.0-debug.apk`.

Version is defined in `app/build.gradle` (`versionCode 1`, `versionName '1.0.0'`).

## Before production
- Configure authenticated backend (e.g. Firebase Authentication + Firestore) and security rules.
- Implement real invite/approval flows, role checks on the server, and per-member consent.
- Implement location foreground service and Android background-location onboarding only after clear user opt-in.
- Add an actual map SDK/tiles and privacy/data retention controls.
- Test on physical Android devices. A standard app cannot prevent users from turning off Location, revoking permissions, force-stopping, or powering off their phone.
