import React from 'react';

// custom hooks
import useScroll from '../../hooks/useScroll';

// styles
import './styles.scss';

const GoUp = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    const [isVisible, setVisible] = React.useState<boolean>(false);

    const handleScroll = (): void => {
        if (!divRef.current) {
            return;
        }
        if (window.scrollY >= divRef.current.offsetTop) {
            setVisible(true);
            return;
        }
        setVisible(false);
    };

    useScroll(handleScroll);

    const handleClick = (): void => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth',
        });
    };

    return (
        <div className={`go-up--container ${isVisible ? '-visible' : ''}`} onClick={handleClick}>
            <i className="icon fa fa-arrow-up" aria-hidden="true"></i>
        </div>
    );
});

export default GoUp;
