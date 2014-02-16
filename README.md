XcodeBuilder
============

A tool to store all your builds to be able to symbolicate your crashlog through all of builds.

Features:

1) Build and store all your builds. I build a project and upload a result by a console script. It's very useful but I overwrite the buld every time. Now I can just write:

    java -jar ./XcodeBuilder -build "xcodebuild -workspace /Users/username/foldername/superapp.xcworkspace -scheme TargetName -configuration Debug build"
    
and app and dSYM files will be saved in separate directory.

2) A symbolcation. Xcode doesn't symbolicate well in my case. When I have a crashlog I run:  

    java -jar ./XcodeBuilder -symbolicate -c "./crash.crash" -o "./symbolicated" -arch armv7

and I will have symbolicated crashlogs for every stored version which have the same UUID with the crashlog (https://developer.apple.com/library/ios/qa/qa1765/_index.html). The tool performs a manuall sybolicaitons according this rules: http://stackoverflow.com/questions/13574933/ios-crash-reports-atos-not-working-as-expected/13576028#13576028

3) Light ftp synchronization. It means all new files since the last synchronization will be uploaded and downloaded.

    java -jar ./XcodeBuilder -ftpsync -l ./uploaded -f ftp/path/folder -n name -p pass -s ./synclog -d 2

4) Create a ftp store for your builds with an ability to download it through html page.
