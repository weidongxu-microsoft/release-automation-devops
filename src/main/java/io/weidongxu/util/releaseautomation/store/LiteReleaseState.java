package io.weidongxu.util.releaseautomation.store;

public enum LiteReleaseState {
    VERIFICATION_FAILED,
    LITE_GEN,
    LITE_GEN_FAILED,
    LITE_GEN_SUCCEEDED,
    PR_DRAFT,
    PR_READY,
    PR_APPROVED,
    PR_MERGED,
    PR_CLOSED,
    PR_CI_SUCCEEDED,
    RELEASE_CI,
    RELEASE_APPROVED,
    RELEASE_SUCCEEDED,
    VERSION_PR,
    VERSION_PR_APPROVED,
    VERSION_PR_MERGED
}
