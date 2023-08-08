#include <jni.h>
#include <string>
#include <fstream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_anative_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject,
        jstring string) {
    jboolean isCopy;
    std::string hello = env->GetStringUTFChars(string,&isCopy);
    std::string add = ", and 'this part was added by a native method'";
    hello+=add;
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT void JNICALL
Java_com_example_anative_MainActivity_saveOnExt(
        JNIEnv* env,
        jobject,
        jstring text,
        jstring path){

    jboolean isCopy;
    std::string content = env->GetStringUTFChars(text,&isCopy);
    std::string folder = env->GetStringUTFChars(path,&isCopy);
    std::string filename = "/FILE.txt";
    folder+=filename;
    std::ofstream ofs (folder);
    ofs << content;
}