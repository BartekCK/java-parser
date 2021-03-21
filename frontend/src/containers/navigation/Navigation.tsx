import React from 'react';

// components
import NavElement from '../../components/nav-element/NavElement';
import LanguageSwitcher from '../../components/language-switcher';

// hooks
import { useTranslation } from 'react-i18next';

// styles
import './styles.scss';

const Navigation: React.FC = () => {
    const { t } = useTranslation();

    return (
        <nav className="navigation--container">
            <ul className="navigation">
                <li>
                    <NavElement routeName="/" name="Parser">
                        <i className="fa fa-home" aria-hidden="true" style={{ padding: '0 5px' }} />
                    </NavElement>
                </li>
                <li>
                    <NavElement routeName="/upload" name={t('common.upload')} />
                </li>
                <li>
                    <NavElement routeName="/editor" name={t('common.editor')} />
                </li>
                <li>
                    <NavElement routeName="/last" name={t('common.lastActivity')} />
                </li>
            </ul>
            <LanguageSwitcher />
        </nav>
    );
};

export default Navigation;
