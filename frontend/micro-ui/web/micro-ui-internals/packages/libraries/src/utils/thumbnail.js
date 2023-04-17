export async function getThumbnails(ids, tenantId, documents = []) {
    const res = await Digit.UploadServices.Filefetch(ids, tenantId);
    if (res.data.fileStoreIds && res.data.fileStoreIds.length !== 0) {
        return {
        thumbs: res.data.fileStoreIds.map((o) => o.url.split(",")[3] || o.url.split(",")[0]),
        fullImage: res.data.fileStoreIds.map((o) => Digit.Utils.getFileUrl(o.url))
        };
    } else {
        return null;
    }
};