plugins {
  alias(libs.plugins.kotlin)
  `maven-publish`
}

dependencies {
  compileOnly(libs.bundles.ktlint)
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
    }
  }

  repositories {
    maven {
      url = uri("https://maven.radsteve.net/public")

      credentials {
        username = System.getenv("RAD_MAVEN_USER")
        password = System.getenv("RAD_MAVEN_TOKEN")
      }
    }
  }
}
