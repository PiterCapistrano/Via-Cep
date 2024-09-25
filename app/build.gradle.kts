// Seção de plugins necessários para o projeto
plugins {
    // Alias para o plugin de aplicação Android
    alias(libs.plugins.androidApplication)

    // Alias para o plugin Kotlin no Android
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    // Define o namespace da aplicação, que também é o pacote base da aplicação
    namespace = "com.pitercapistrano.viacep"

    // Define a versão do SDK de compilação que será usada
    compileSdk = 34

    defaultConfig {
        // Identificador único da aplicação
        applicationId = "com.pitercapistrano.viacep"

        // Define a versão mínima do SDK Android que o app suporta
        minSdk = 24

        // Define a versão de destino do SDK Android que o app foi projetado para rodar
        targetSdk = 34

        // Define a versão do código da aplicação (usado para controle de atualizações)
        versionCode = 1

        // Define a versão de nome da aplicação (visível para o usuário)
        versionName = "1.0"

        // Define o runner de testes de instrumentação para testes em dispositivos Android
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // Configurações do tipo de build "release"
        release {
            // Define se o código será minimizado (false significa que não)
            isMinifyEnabled = false

            // Arquivos de configuração ProGuard para otimização e ofuscação do código no build release
            proguardFiles(
                // Usa o arquivo ProGuard padrão para otimização no Android
                getDefaultProguardFile("proguard-android-optimize.txt"),

                // Arquivo customizado de regras ProGuard
                "proguard-rules.pro"
            )
        }
    }

    // Configurações de compatibilidade de código fonte com diferentes versões do Java
    compileOptions {
        // Define a versão de origem do Java como 1.8
        sourceCompatibility = JavaVersion.VERSION_1_8

        // Define a versão de destino do Java como 1.8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Configurações para o compilador Kotlin
    kotlinOptions {
        // Define a versão do bytecode JVM como 1.8 para Kotlin
        jvmTarget = "1.8"
    }

    // Ativa o ViewBinding para facilitar o acesso às views no layout
    viewBinding {
        enable = true // Ativa o ViewBinding
    }
}

dependencies {
    // Dependências necessárias para o funcionamento da aplicação

    // Biblioteca AndroidX Core com extensões Kotlin (ktx)
    implementation(libs.androidx.core.ktx)

    // Biblioteca AndroidX AppCompat para compatibilidade entre versões
    implementation(libs.androidx.appcompat)

    // Biblioteca de Material Design
    implementation(libs.material)

    // Biblioteca AndroidX Activity para componentes de Activity
    implementation(libs.androidx.activity)

    // Biblioteca AndroidX ConstraintLayout para layouts responsivos
    implementation(libs.androidx.constraintlayout)

    // Biblioteca Retrofit 2 para fazer requisições HTTP
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Conversor GSON para Retrofit, para converter JSON em objetos Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Dependência para testes unitários com JUnit
    testImplementation(libs.junit)

    // Dependência para testes instrumentados com JUnit no Android
    androidTestImplementation(libs.androidx.junit)

    // Dependência para o framework de testes Espresso para UI
    androidTestImplementation(libs.androidx.espresso.core)
}
