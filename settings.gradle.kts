plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "study_dgs"
include("apps")
include("apps:fixity")
findProject(":apps:fixity")?.name = "fixity"
include("apps:proxy")
findProject(":apps:proxy")?.name = "proxy"
include("apps:proxy:user")
findProject(":apps:proxy:user")?.name = "user"
include("apps:fixity:user")
findProject(":apps:fixity:user")?.name = "user"
include("apps")
include("apps:fixity")
findProject(":apps:fixity")?.name = "fixity"
include("apps:proxy")
findProject(":apps:proxy")?.name = "proxy"
