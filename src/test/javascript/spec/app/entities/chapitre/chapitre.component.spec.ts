/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { ChapitreComponent } from '../../../../../../main/webapp/app/entities/chapitre/chapitre.component';
import { ChapitreService } from '../../../../../../main/webapp/app/entities/chapitre/chapitre.service';
import { Chapitre } from '../../../../../../main/webapp/app/entities/chapitre/chapitre.model';

describe('Component Tests', () => {

    describe('Chapitre Management Component', () => {
        let comp: ChapitreComponent;
        let fixture: ComponentFixture<ChapitreComponent>;
        let service: ChapitreService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [ChapitreComponent],
                providers: [
                    ChapitreService
                ]
            })
            .overrideTemplate(ChapitreComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChapitreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChapitreService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Chapitre(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.chapitres[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
