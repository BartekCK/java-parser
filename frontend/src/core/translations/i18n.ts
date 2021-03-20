import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translations from './translation.json';

i18n.use(initReactI18next).init({
    debug: true,
    resources: translations,
    lng: 'pl',
    fallbackLng: 'pl',
    whitelist: ['pl', 'en'],
    interpolation: {
        escapeValue: false,
    },
});

export default i18n;
