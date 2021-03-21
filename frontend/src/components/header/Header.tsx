import React from 'react';

// components
import Carousel from '../carousel';

// hooks
import { useTranslation } from 'react-i18next';

// styles
import './styles.scss';

const Header: React.FC = () => {
    const { t } = useTranslation();

    return (
        <div className="header--wrapper">
            <h1 className="title">{t('header')}</h1>
            <Carousel />
        </div>
    );
};

export default Header;
