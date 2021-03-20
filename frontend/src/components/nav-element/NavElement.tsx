import React from 'react';
import { Link } from 'react-router-dom';

// styles
import './styles.scss';

type Props = {
    routeName: string;
    name?: string;
    children?: React.ReactElement;
};

const NavElement: React.FC<Props> = ({ children, routeName, name }: Props) => {
    const handleClick = (): void => {
        console.log('Logo Clik');
    };
    return (
        <Link className="link" to={routeName} onClick={handleClick}>
            {children}
            {name}
        </Link>
    );
};

export default NavElement;
