import React from 'react';

// components
import Modal from '../modal';
import ExtensionListComponent from '../extension-list-component';

// hooks
import { useTranslation } from 'react-i18next';

// styles
import './styles.scss';

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
                <ExtensionListComponent />
                <div className="btn-place" onClick={closeInfoModal}>
                    <button className="btn btn-primary">{t('common.okay')}</button>
                </div>
            </div>
        </Modal>
    );
};

export default UploadPlaceInfo;
