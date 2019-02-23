import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { StoreSharedModule } from 'app/shared';
import {
    JobHistoryComponent,
    JobHistoryDetailComponent,
    JobHistoryUpdateComponent,
    JobHistoryDeletePopupComponent,
    JobHistoryDeleteDialogComponent,
    jobHistoryRoute,
    jobHistoryPopupRoute
} from './';

const ENTITY_STATES = [...jobHistoryRoute, ...jobHistoryPopupRoute];

@NgModule({
    imports: [StoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JobHistoryComponent,
        JobHistoryDetailComponent,
        JobHistoryUpdateComponent,
        JobHistoryDeleteDialogComponent,
        JobHistoryDeletePopupComponent
    ],
    entryComponents: [JobHistoryComponent, JobHistoryUpdateComponent, JobHistoryDeleteDialogComponent, JobHistoryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StoreJobHistoryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
