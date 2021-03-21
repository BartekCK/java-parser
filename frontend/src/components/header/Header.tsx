import React from 'react';

// components
import Carousel from '../carousel';

// styles
import './styles.scss';

const Header: React.FC = () => {
    return (
        <div className="header--wrapper">
            <h1>Konwertuj szybko i łatwo pliki bądź kod pomiędzy wybranymi formatami</h1>
            <Carousel />
        </div>
    );
};

export default Header;
