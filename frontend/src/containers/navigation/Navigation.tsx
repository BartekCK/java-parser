import React from 'react';

// components
import NavElement from '../../components/nav-element/NavElement';
import LanguageSwitcher from '../../components/language-switcher';

// hooks
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom';

// custom hooks
import useScroll from '../../hooks/useScroll';

// types
import { IRefWrapper } from '../../core/types';

// styles
import './styles.scss';

const Navigation = React.forwardRef((props, wrapperRef: React.RefObject<IRefWrapper>) => {
    const { t } = useTranslation();

    const history = useHistory();

    const handleScroll = () => {
        const currentOffset: number = window.scrollY;
        const objOfHtmlDiv: IRefWrapper | null = wrapperRef.current;
        if (!objOfHtmlDiv) {
            return;
        }
        const { homeRef, activityRef, editorRef, uploadRef } = objOfHtmlDiv;
        if (!homeRef.current || !activityRef.current || !editorRef.current || !uploadRef.current) {
            return;
        }
        // switch (true) {
        //     case currentOffset >= 0 && currentOffset < uploadRef.current.offsetTop:
        //         history.push('/');
        //         break;
        //     case currentOffset >= uploadRef.current.offsetTop && currentOffset < editorRef.current.offsetTop:
        //         history.push('/upload' );
        //         break;
        //     case currentOffset >= editorRef.current.offsetTop && currentOffset < activityRef.current.offsetTop:
        //         history.push('/editor');
        //         break;
        //     case currentOffset >= activityRef.current.offsetTop:
        //         history.push('/last');
        //         break;
        // }
    };

    useScroll(handleScroll);

    return (
        <nav className="navigation--container">
            <ul className="navigation">
                <li>
                    <NavElement routeName="/" name="Parser" ref={wrapperRef.current?.homeRef}>
                        <i className="fa fa-home" aria-hidden="true" style={{ padding: '0 5px' }} />
                    </NavElement>
                </li>
                <li>
                    <NavElement routeName="/" name={t('common.upload')} ref={wrapperRef.current?.uploadRef} />
                </li>
                <li>
                    <NavElement routeName="/" name={t('common.editor')} ref={wrapperRef.current?.editorRef} />
                </li>
                <li>
                    <NavElement
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
