import React from 'react';

// styles
import './styles.scss';

type Props = {
    isOpen?: boolean;
    isVisible?: boolean;
    children: React.ReactNode;
};

const ModalInner: React.FC<Props> = (props: Props) => {
    const { children, isVisible } = props;
    console.log(isVisible);
    return (
        <div className={`modal--wrapper ${isVisible ? '-visible' : ''}`}>
            <div className="container">
                <i className="close fa fa-times" aria-hidden="true"></i>
                {children}
            </div>
        </div>
    );
};

export default ModalInner;
