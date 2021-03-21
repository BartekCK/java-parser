import React from 'react';

// hooks
import { useTranslation } from 'react-i18next';

// styles
import './styles.scss';

// assets
import UploadLogo from '../../assets/convert-extensions/global.png';

const UploadPlace = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    const { t } = useTranslation();

    return (
        <div ref={divRef} className="upload--container">
            <img className="upload-logo" src={UploadLogo} alt="upload-logo" />
            <button className="upload-btn">
                <i className="icon fa fa-plus-square" aria-hidden="true"></i>
                {t('common.upload')}
            </button>
            <span>{t('message.uploadLabel')}</span>
        </div>
    );
});

export default UploadPlace;
