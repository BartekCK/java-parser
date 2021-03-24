import React from 'react';
import ReactDOM from 'react-dom';

// components
import ModalInner from './ModalInner';

import './styles.scss';

type Props = {
    children: React.ReactNode;
    isOpen?: boolean;
};

const Modal: React.FC<Props> = (props: Props) => {
    const { children, isOpen } = props;

    React.useEffect(() => {
        if (isOpen) {
            document.body.classList.add('hide--overflow');
        }
        return () => {
            if (!isOpen) {
                document.body.classList.remove('hide--overflow');
            }
        };
    }, [isOpen]);

    return ReactDOM.createPortal(
        <ModalInner isVisible={isOpen} isOpen={isOpen}>
            {children}
        </ModalInner>,
        document.body,
    );
};

export default Modal;
