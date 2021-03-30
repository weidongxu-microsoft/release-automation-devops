# Usage

Define environment variables:
- `USER` username
- `PAT` personal access token from Azure DevOps
- `BUILD_ID` the build ID

## Configuration for autorest

> see https://aka.ms/autorest

This is the AutoRest configuration file for Dev.

```yaml
title: DevClient
namespace: com.azure.dev
license-header: MICROSOFT_MIT_SMALL

input-file:
- build.json
- pipelines.json
```