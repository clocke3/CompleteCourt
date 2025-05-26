import { expect, test } from '@playwright/test';

test('visits the app root url', async ({ page }) => {
  await page.goto('/');
  // check buttons
  expect((await page.getByRole('button').all()).length == 3);
})

test('visits the loading url', async ({ page }) => {
  await page.goto('/');

  // click first button on page
  await page.locator('button:text("Start 1 Minute Game")').click();

  const newUrl = page.url();

  expect(newUrl.includes('/loading'));
})

test('visits the game url and checks out elements', async ({ page }) => {
  await page.goto('/');

  // click first button on page
  await page.locator('button:text("Start 1 Minute Game")').click();

  // set timeout
  setTimeout(() => 3000);

  const gameUrl = page.url();

  expect(gameUrl.includes('/game'));
})
