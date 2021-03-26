import React from 'react';

// components
import NavElement from '../../components/nav-element/NavElement';
import LanguageSwitcher from '../../components/language-switcher';

// hooks
import { useTranslation } from 'react-i18next';

// custom hooks
import useCLickOutside from '../../hooks/useClickOutside';

// types
import { IRefWrapper } from '../../core/types';

// styles
import './styles.scss';

const Navigation = React.forwardRef((props, wrapperRef: React.RefObject<IRefWrapper>) => {
    const [isMenuVisible, setMenuVisible] = React.useState<boolean>(false);

    const { t } = useTranslation();

    const navWrapperRef = React.useRef<HTMLUListElement>(null);

    const handleClick = (): void => {
        setMenuVisible((prev) => !prev);
    };

    const handleLiClick = (): void => {
        setMenuVisible(false);
    };

    useCLickOutside(navWrapperRef, () => setMenuVisible(false));

    return (
        <nav className="navigation--container">
            <button className="nav-btn" onClick={handleClick}>
                <i className="fa fa-home" aria-hidden="true" />
            </button>
            <ul className={`navigation ${isMenuVisible ? '-visible' : ''}`} ref={navWrapperRef}>
                <li>
                    <NavElement onClick={handleLiClick} routeName="/" name="Parser" ref={wrapperRef.current?.homeRef}>
                        <i className="fa fa-home" aria-hidden="true" style={{ padding: '0 5px' }} />
                    </NavElement>
                </li>
                <li>
                    <NavElement
                        onClick={handleLiClick}
                        routeName="/"
                        name={t('common.upload')}
                        ref={wrapperRef.current?.uploadRef}
                    />
                </li>
                <li>
                    <NavElement
                        onClick={handleLiClick}
                        routeName="/"
                        name={t('common.editor')}
                        ref={wrapperRef.current?.editorRef}
                    />
                </li>
                <li>
                    <NavElement
                        onClick={handleLiClick}
                        routeName="/"
                        name={t('common.lastActivity')}
                        ref={wrapperRef.current?.activityRef}
                    />
                </li>
            </ul>
            <LanguageSwitcher />
        </nav>
    );
});

export default Navigation;
