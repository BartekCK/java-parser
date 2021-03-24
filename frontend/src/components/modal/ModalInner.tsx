import React from 'react';

// styles
import './styles.scss';

type Props = {
    isOpen?: boolean;
    isVisible?: boolean;
    children: React.ReactNode;
    closeModal: () => void;
};

const ModalInner: React.FC<Props> = (props: Props) => {
    const { children, isVisible, closeModal } = props;

    return (
        <div className={`modal--wrapper ${isVisible ? '-visible' : ''}`} onClick={closeModal}>
            <div className="container" onClick={(event) => event.stopPropagation()}>
                <i className="close fa fa-times" aria-hidden="true" onClick={closeModal} />
                {children}
            </div>
        </div>
    );
};

export default ModalInner;
