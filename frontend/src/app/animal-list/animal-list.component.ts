import { Component, OnInit } from '@angular/core';
import {Animal} from '../api/animal';
import {AnimalService} from '../service/animal.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-animal-list',
  templateUrl: './animal-list.component.html',
  styleUrls: ['./animal-list.component.scss']
})
export class AnimalListComponent implements OnInit {

  animals: Array<Animal>;

  constructor(private animalService: AnimalService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    const data = this.route.snapshot.data;
    this.animals = data.animals;
  }

  createAnimal() {
    this.router.navigate(['/animal-form']);
  }

  deleteAnimal(animal: Animal) {
    this.animalService.delete(animal)
      .subscribe(() => {
        this.ngOnInit();
      });
  }
}