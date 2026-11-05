
def call() {

    // Get Git repository URL
    def remoteUrl = bat(
        script: '@git config --get remote.origin.url',
        returnStdout: true
    ).trim()

    if (!remoteUrl) {
        error('Unable to determine Git repository URL.')
    }

    // Extract repository name
    def normalizedUrl = remoteUrl
        .replace('\\', '/')
        .replace(':', '/')

    def repoName = normalizedUrl.tokenize('/').last()
        .replaceFirst(/\.git$/, '')
        .toLowerCase()
        .replaceAll(/[^a-z0-9]+/, '-')
        .replaceAll(/^-+|-+$/, '')

    if (!repoName) {
        error('Invalid repository name.')
    }

    // Determine current branch
    def branch = env.BRANCH_NAME ?: env.GIT_BRANCH

    if (!branch || branch == 'HEAD') {
        branch = bat(
            script: '@git branch --show-current',
            returnStdout: true
        ).trim()
    }

    if (!branch) {
        error('Unable to determine Git branch.')
    }

    // Normalize branch name
    branch = branch
        .replaceFirst(/^refs\/heads\//, '')
        .replaceFirst(/^origin\//, '')
        .toLowerCase()
        .replaceAll(/[^a-z0-9_.-]+/, '-')
        .replaceAll(/^[.-]+|[.-]+$/, '')

    branch = branch.take(128)
        .replaceAll(/[.-]+$/, '')

    if (!branch) {
        error('Invalid branch name.')
    }

    // Export metadata to Jenkins environment
    env.REPO_NAME = repoName
    env.IMAGE_NAME = "localhost/${repoName}"
    env.IMAGE_TAG = branch

    echo '===================================='
    echo 'Container Image Metadata'
    echo '===================================='
    echo "Repository : ${env.REPO_NAME}"
    echo "Image Name : ${env.IMAGE_NAME}"
    echo "Image Tag  : ${env.IMAGE_TAG}"
    echo "Full Image : ${env.IMAGE_NAME}:${env.IMAGE_TAG}"
    echo '===================================='
}
