import React from 'react';

// components
import UploadPlaceWithFile from './UploadPlaceWithFile';

// custom hooks
import useModalVisibleMange from '../../hooks/useModalVisibleManage';

// hooks
import { useTranslation } from 'react-i18next';
import { DropEvent, FileRejection, useDropzone } from 'react-dropzone';

// styles
import './styles.scss';

// assets
import UploadLogo from '../../assets/convert-extensions/global.png';
import { AllowFileType } from './types/enums';
import Modal from '../modal';
import UploadPlaceInfo from './UploadPlaceInfo';

const UploadPlace = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    const [file, setFile] = React.useState<File | null>(null);
    const { t } = useTranslation();

    const [isInfoModalOpen, openInfoModal, closeInfoModal] = useModalVisibleMange();

    const allowTypes: (string | AllowFileType)[] = React.useMemo(
        () => [AllowFileType.csv, AllowFileType.json, AllowFileType.xml, AllowFileType.yaml],
        [],
    );

    const onDrop = React.useCallback((acceptedFiles: File[], fileRejections: FileRejection[], event: DropEvent) => {
        if (acceptedFiles.length > 1 || acceptedFiles.length === 0) {
            return;
        }
        const singleFile: File = acceptedFiles[0];
        if (!allowTypes.includes(singleFile.type)) {
            console.log('Extension not allowed');
        }
        setFile(singleFile);
    }, []);

    React.useEffect(() => {
        if (!file) {
            return;
        }
        console.log(file);
    }, [file]);

    const { getRootProps, getInputProps } = useDropzone({ onDrop });

    const handleClick = (event): void => {
        event.stopPropagation();
    };

    return (
        <div ref={divRef} className="upload--container" {...getRootProps()} onClick={handleClick}>
            <i className="ask fa fa-question" aria-hidden="true" onClick={openInfoModal} />
            {file ? (
                <UploadPlaceWithFile file={file} />
            ) : (
                <React.Fragment>
                    <img className="upload-logo" src={UploadLogo} alt="upload-logo" />
                    <button className="upload-btn" {...getRootProps()}>
                        <input {...getInputProps()} />
                        <i className="icon fa fa-plus-square" aria-hidden="true"></i>
                        {t('common.upload')}
                    </button>
                    <span>{t('message.uploadLabel')}</span>
                </React.Fragment>
            )}
            <UploadPlaceInfo closeInfoModal={closeInfoModal} isInfoModalOpen={isInfoModalOpen} />
        </div>
    );
});

export default UploadPlace;
