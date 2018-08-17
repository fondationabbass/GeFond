/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GeFondTestModule } from '../../../test.module';
import { CaisseComponent } from '../../../../../../main/webapp/app/entities/caisse/caisse.component';
import { CaisseService } from '../../../../../../main/webapp/app/entities/caisse/caisse.service';
import { Caisse } from '../../../../../../main/webapp/app/entities/caisse/caisse.model';

describe('Component Tests', () => {

    describe('Caisse Management Component', () => {
        let comp: CaisseComponent;
        let fixture: ComponentFixture<CaisseComponent>;
        let service: CaisseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GeFondTestModule],
                declarations: [CaisseComponent],
                providers: [
                    CaisseService
                ]
            })
            .overrideTemplate(CaisseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CaisseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CaisseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Caisse(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.caisses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
