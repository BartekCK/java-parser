import React from 'react';
import { Link } from 'react-router-dom';

// styles
import './styles.scss';

type Props = {
    routeName: string;
    name?: string;
    children?: React.ReactElement;
};

const NavElement = React.forwardRef((props: Props, divRef: React.RefObject<HTMLDivElement>) => {
    const { name, routeName, children } = props;

    const handleClick = (): void => {
        if (!divRef.current) {
            return;
        }
        window.scrollTo({
            top: divRef.current.offsetTop,
            behavior: 'smooth',
        });
    };

    return (
        <Link className="link" to={routeName} onClick={handleClick}>
            {children}
            {name}
        </Link>
    );
});

export default NavElement;
