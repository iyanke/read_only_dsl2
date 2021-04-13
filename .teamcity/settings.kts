import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {

    vcsRoot(HttpsGithubComIyankeBigdataRefsHeadsMaster)

    buildType(Build)
    buildType(Build22)

    template(Temp)
}

object Build : BuildType({
    name = "Build"
    paused = true

    params {
        param("build", "${Build22.depParamRefs["build22_param"]}")
        param("Build_param", "1")
    }

    vcs {
        root(HttpsGithubComIyankeBigdataRefsHeadsMaster)
    }

    steps {
        script {
            scriptContent = "echo a"
        }
    }

    triggers {
        vcs {
        }
        schedule {
            triggerBuild = onWatchedBuildChange {
                buildType = "${Build22.id}"
            }
        }
    }

    dependencies {
        dependency(Build22) {
            snapshot {
            }

            artifacts {
                artifactRules = "**/*.*"
            }
        }
    }
})

object Build22 : BuildType({
    name = "Build22"
    description = "1"
    paused = true

    params {
        param("build22_param", "2")
    }

    vcs {
        root(HttpsGithubComIyankeBigdataRefsHeadsMaster)
    }

    steps {
        script {
            scriptContent = "echo a"
        }
    }

    triggers {
        vcs {
        }
    }
})

object Temp : Template({
    name = "temp"

    vcs {
        root(HttpsGithubComIyankeBigdataRefsHeadsMaster)
    }

    steps {
        script {
            id = "RUNNER_110"
            scriptContent = "echo a"
        }
    }

    triggers {
        vcs {
            id = "vcsTrigger"
        }
    }
})

object HttpsGithubComIyankeBigdataRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/iyanke/bigdata#refs/heads/master"
    url = "https://github.com/iyanke/bigdata"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "iyanke"
        password = "credentialsJSON:7c44fbf5-6ed1-4ecb-ac02-060f37b97bf7"
    }
})
