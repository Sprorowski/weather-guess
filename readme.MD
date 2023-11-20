copy [.env.sample](.env.sample) in you root directory as .env

now on you Run/Debug Configurations window go to EnvFile tab add the .env file


# WSL2 Users

Install the sdkman
```shell
sdk': sdk i java 17.0.8-graal
```

run Gradle

if a Configuration Deploy is not already create.
create a Configuration with kotlin, add `weather.sprorowski.io.ApplicationKt` in the main class,
and set the weather.sprorowski.io.main as classpath of module, select the correct wsl2 JRE
@argfile (Java 9+) as shorten command line.



