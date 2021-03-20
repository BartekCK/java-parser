import React from 'react';

// hooks
import { useTranslation } from 'react-i18next';

// assets
import EnglishFlag from '../../assets/en.png';
import PolishFlag from '../../assets/pl.svg';

// styles
import './styles.scss';

const LanguageSwitcher: React.FC = () => {
    const { i18n } = useTranslation();

    const handleClick = (language: string): void => {
        i18n.changeLanguage(language);
    };

    const setCurrentLanguage = (lang: string): string => {
        return i18n.language === lang ? 'flag -current' : 'flag';
    };

    return (
        <div className="language--switcher">
            <img onClick={() => handleClick('pl')} className={setCurrentLanguage('pl')} src={PolishFlag} />
            <img onClick={() => handleClick('en')} className={setCurrentLanguage('en')} src={EnglishFlag} />
        </div>
    );
};

export default LanguageSwitcher;
