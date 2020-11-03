TrNgGrid.setUpI18n = function(){

    var defaultTranslation = {};
    var enTranslation = angular.extend({}, defaultTranslation);
    TrNgGrid.translations["en"] = enTranslation;


    var deTranslation = angular.extend({}, enTranslation,
        {
            "Page":"Seite",
            "First Page": "Erste Seite",
            "Next Page": "Nächste Seite",
            "Previous Page": "Vorherige Seite",
            "Last Page": "Letzte Seite",
            "Sort": "Sortieren",
            "displayed": "angezeigt",
            "in total": "insgesamt"
        });
    TrNgGrid.translations["de"] = deTranslation;

    var frTranslation = angular.extend({}, enTranslation,
        {
            "Page":"Page",
            "First Page": "Première page",
            "Next Page": "Page suivante",
            "Previous Page": "Page précédente",
            "Last Page": "Dernière page",
            "Sort": "Trier",
            "displayed": "affichés",
            "in total": "au total"
        });
    TrNgGrid.translations["fr"] = frTranslation;

    var itTranslation = angular.extend({}, enTranslation,
        {
            "Page":"Pagina",
            "First Page": "Prima pagina",
            "Next Page": "Prossima pagina",
            "Previous Page": "Pagina precedente",
            "Last Page": "Ultima pagina",
            "Sort": "Ordinare",
            "displayed": "Visibili",
            "in total": "in totale"
        });
    TrNgGrid.translations["it"] = itTranslation;


}