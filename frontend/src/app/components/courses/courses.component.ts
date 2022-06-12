import { Component, OnInit } from '@angular/core';
import { Course } from '../../models/course';
import { User } from '../../models/user';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {
  user?: User | null = JSON.parse(localStorage.getItem('user')!);

  courses?: Course[];
  totalItems?: number;
  totalPages?: number;
  itemsPerPage?: number;
  currentPage?: number;
  filter?: string = "name";
  filterValue?: string;
  sortBy?: string = this.filter;
  desc?: boolean;

  filters: any = {
    name: 'Name',
    description: 'Description',
    createdBy: 'Creator',
  };
  filterKeys = Object.keys(this.filters);


  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.courseService.getCourses().subscribe((pagination) => {
      this.courses = pagination.items;
      this.itemsPerPage = pagination.itemsPerPage;
      this.totalPages = pagination.totalPages;
      this.totalItems = pagination.totalItems;
      this.currentPage = pagination.currentPage;
    });
  }

  handlePageEvent(event: any) {
    this.currentPage = event.pageIndex;
    this.itemsPerPage = event.pageSize;
    this.fetchCourses();
  }

  onSubmitSearch() {
    this.fetchCourses();
  }

  fetchCourses() {
    this.courseService.getCourses(
      this.filter,
      this.filterValue,
      this.currentPage,
      this.itemsPerPage,
      this.sortBy,
      this.desc
    ).subscribe((pagination) => {
      this.courses = pagination.items;
      this.itemsPerPage = pagination.itemsPerPage;
      this.totalPages = pagination.totalPages;
      this.totalItems = pagination.totalItems;
      this.currentPage = pagination.currentPage;
    });
  }
}
