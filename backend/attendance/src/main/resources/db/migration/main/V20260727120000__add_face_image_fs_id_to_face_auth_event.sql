-- Store the face verification image in egov-filestore and keep only its reference here,
-- instead of the inline base64 in face_image. Existing rows keep their base64 in face_image;
-- new rows populate face_image_fs_id and leave face_image null.
ALTER TABLE eg_wms_face_auth_event
    ADD COLUMN IF NOT EXISTS face_image_fs_id VARCHAR(256);

COMMENT ON COLUMN eg_wms_face_auth_event.face_image_fs_id IS 'egov-filestore fileStoreId of the cropped face JPEG used for verification (replaces inline base64 in face_image)';
