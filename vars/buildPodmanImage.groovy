
def call() {

    // 1. Validate image metadata
    if (!env.IMAGE_NAME || !env.IMAGE_TAG) {
        error('IMAGE_NAME or IMAGE_TAG is missing. Run prepareImageMetadata() first.')
    }

    def image = "${env.IMAGE_NAME}:${env.IMAGE_TAG}"

    echo '===================================='
    echo 'Building Podman Container Image'
    echo '===================================='
    echo "Image: ${image}"

    // 2. Build image using Podman
    bat '''
        set "APPDATA=C:\\Users\\rinst\\AppData\\Roaming"
        podman version
        podman build -t "%IMAGE_NAME%:%IMAGE_TAG%" .
    '''

    echo "Successfully built image: ${image}"
}
