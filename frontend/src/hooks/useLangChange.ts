import React from 'react';

// hooks
import { useCookies } from 'react-cookie';
import { useTranslation } from 'react-i18next';

function useLangChange() {
    const [cookies, setCookie] = useCookies(['cookie-name']);
    const { i18n } = useTranslation();

    React.useEffect(() => {
        if (!cookies['parserLang']) {
            return;
        }
        i18n.changeLanguage(cookies['parserLang']);
    }, []);

    React.useEffect(() => {
        setCookie('parserLang', i18n.language);
    }, [i18n.language]);
}

export default useLangChange;
