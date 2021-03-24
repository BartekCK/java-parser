import React from 'react';

// components
import Modal from '../modal';

// hooks
import { useTranslation } from 'react-i18next';

// styles
import './styles.scss';

// assets
import JsonDoc from '../../assets/convert-extensions/json-doc.png';
import CsvDoc from '../../assets/convert-extensions/csv-doc.png';
import YamlDoc from '../../assets/convert-extensions/yaml-doc.png';
import XmlDoc from '../../assets/convert-extensions/xml-doc.png';

type Props = {
    isInfoModalOpen: boolean;
    closeInfoModal: () => void;
};

const UploadPlaceInfo: React.FC<Props> = (props: Props) => {
    const { closeInfoModal, isInfoModalOpen } = props;

    const { t } = useTranslation();

    return (
        <Modal closeModal={closeInfoModal} isOpen={isInfoModalOpen}>
            <div className="info--wrapper">
                <p className="title" dangerouslySetInnerHTML={{ __html: t('message.infoUpload.title') }} />
                <p className="content" dangerouslySetInnerHTML={{ __html: t('message.infoUpload.content') }} />
                <div className="extensions">
                    <img src={JsonDoc} className="single-extension" />
                    <img src={CsvDoc} className="single-extension" />
                    <img src={YamlDoc} className="single-extension" />
                    <img src={XmlDoc} className="single-extension" />
                </div>
                <div className="btn-place" onClick={closeInfoModal}>
                    <button className="btn btn-primary">{t('common.okay')}</button>
                </div>
            </div>
        </Modal>
    );
};

export default UploadPlaceInfo;
