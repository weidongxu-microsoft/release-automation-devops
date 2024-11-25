package io.weidongxu.util.releaseautomation;

public class Configure {

    // the service name at https://github.com/Azure/azure-rest-api-specs/tree/main/specification
    private String swagger;

    // url to the tspconfig.yaml file, can be either from main branch, or specific commitID
    // e.g. From main branch: https://github.com/Azure/azure-rest-api-specs/blob/main/specification/mongocluster/DocumentDB.MongoCluster.Management/tspconfig.yaml
    // From specific commitID: https://github.com/Azure/azure-rest-api-specs/blob/7605afe88e3201dc25ce0881c2e49fe1b6bbdd54/specification/mongocluster/DocumentDB.MongoCluster.Management/tspconfig.yaml
    private String tspConfig;

    // preview release
    private boolean autoVersioning = true;

    // used only if preview=false
    // if preview=true, uses current next release version in https://github.com/Azure/azure-sdk-for-java/blob/main/eng/versioning/version_client.txt
    private String version;

    // tag in readme.md
    // prompt in stdin, if not provided
    private String tag;

    // the service name at https://github.com/Azure/azure-sdk-for-java/tree/main/sdk
    // same as swagger, if not provided
    private String service;

    // e.g. "containers" for namespace "com.azure.resourcemanager.hdinsight.containers"
    private String suffix;

    // generate tests
    private Boolean tests;

    // azure-sdk-for-java repository branch to run codegen pipeline
    // this will not affect Automation PR's target branch, which will always be "main"
    private String sdkRepoBranch = "main";

    public String getSdkRepoBranch() {
        return sdkRepoBranch;
    }

    public void setSdkRepoBranch(String sdkRepoBranch) {
        this.sdkRepoBranch = sdkRepoBranch;
    }

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public String getTspConfig() {
        return tspConfig;
    }

    public void setTspConfig(String tspConfig) {
        this.tspConfig = tspConfig;
    }

    public boolean isAutoVersioning() {
        return autoVersioning;
    }

    public void setAutoVersioning(boolean autoVersioning) {
        this.autoVersioning = autoVersioning;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getTests() {
        return tests;
    }

    public void setTests(Boolean tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Configure{" +
            "swagger='" + swagger + '\'' +
            ", tspConfig='" + tspConfig + '\'' +
            ", autoVersioning=" + autoVersioning +
            ", version='" + version + '\'' +
            ", tag='" + tag + '\'' +
            ", service='" + service + '\'' +
            ", suffix='" + suffix + '\'' +
            ", tests=" + tests +
            '}';
    }
}
