/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { EntretienComponent } from '../../../../../../main/webapp/app/entities/entretien/entretien.component';
import { EntretienService } from '../../../../../../main/webapp/app/entities/entretien/entretien.service';
import { Entretien } from '../../../../../../main/webapp/app/entities/entretien/entretien.model';

describe('Component Tests', () => {

    describe('Entretien Management Component', () => {
        let comp: EntretienComponent;
        let fixture: ComponentFixture<EntretienComponent>;
        let service: EntretienService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [EntretienComponent],
                providers: [
                    EntretienService
                ]
            })
            .overrideTemplate(EntretienComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntretienComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntretienService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Entretien(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.entretiens[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
